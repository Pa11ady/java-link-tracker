package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.linkparser.dto.GitInfo;
import ru.tinkoff.edu.java.linkparser.dto.LinkInfo;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowInfo;
import ru.tinkoff.edu.java.linkparser.parser.GitLinkParser;
import ru.tinkoff.edu.java.linkparser.parser.LinkParser;
import ru.tinkoff.edu.java.linkparser.parser.StackOverflowLinkParser;
import ru.tinkoff.edu.java.scrapper.botclient.BotClient;
import ru.tinkoff.edu.java.scrapper.botclient.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.github.GitHubClient;
import ru.tinkoff.edu.java.scrapper.github.dto.RepoResponse;
import ru.tinkoff.edu.java.scrapper.model.Chat;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.SubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.stackoverflow.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.stackoverflow.dto.QuestionResponse;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateServiceImpl implements UpdateService {
    private final LinkRepository linkRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final BotClient botClient;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    @Value("${app.link-age}")
    private Long updateAge;

    @Override
    public void update() {
        List<Link> oldLinks = linkRepository.findOldLinks(OffsetDateTime.now().minusSeconds(updateAge));
        for (Link link : oldLinks) {
            LinkInfo linkInfo = parseUrl(link.getUrl());
            if (linkInfo == null) {
                continue;
            }
            if (linkInfo instanceof GitInfo gitInfo) {
                log.info("Git: " + gitInfo.userName() + "/" + gitInfo.repoName());
                updateFromGutHub(gitInfo.userName(), gitInfo.repoName(), link);

            } else if (linkInfo instanceof StackOverflowInfo stackOverflowInfo) {
                log.info("StackOverflow: " + stackOverflowInfo.id());
                updateFromStackOverflow(stackOverflowInfo.id(), link);
            }
        }
    }

    private void updateFromGutHub(String user, String repository, Link link) {
        RepoResponse response = gitHubClient.findRepository(user, repository);
        if (link.getUpdated().isBefore(response.updated_at())) {
            link.setUpdated(response.updated_at());
            linkRepository.update(link);
            notifyBot(link);
            log.info("Обновляем ссылку...");
            log.info(response.toString());
        }
    }

    private void updateFromStackOverflow(String questionId, Link link) {
        QuestionResponse response = stackOverflowClient.findQuestion(questionId);
        OffsetDateTime lastEdiDate = response.items().get(0).last_edit_date();
        if (link.getUpdated().isBefore(lastEdiDate)) {
            link.setUpdated(lastEdiDate);
            linkRepository.update(link);
            notifyBot(link);
            log.info("Обновляем ссылку...");
            log.info(response.toString());
        }
    }

    private LinkInfo parseUrl(String url) {
        LinkParser parserChain = new GitLinkParser(new StackOverflowLinkParser(null));
        return parserChain.parseLink(url);
    }

    public void notifyBot(Link link) {
        List<Long> tgChatIds = subscriptionRepository.findAllByLink(link.getId())
                .stream()
                .map(Chat::getId)
                .toList();

        LinkUpdate update = new LinkUpdate(
                link.getId(),
                URI.create(link.getUrl()),
                "есть обновление",
                tgChatIds
        );
        botClient.sendUpdate(update);
    }
}
