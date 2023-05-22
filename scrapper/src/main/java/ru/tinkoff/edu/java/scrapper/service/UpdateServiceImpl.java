package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.linkparser.dto.GitInfo;
import ru.tinkoff.edu.java.linkparser.dto.LinkInfo;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowInfo;
import ru.tinkoff.edu.java.linkparser.parser.GitLinkParser;
import ru.tinkoff.edu.java.linkparser.parser.LinkParser;
import ru.tinkoff.edu.java.linkparser.parser.StackOverflowLinkParser;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;
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
    private final MessageService messageService;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    @Value("${app.link-age}")
    private Long updateAge;

    @Transactional
    @Override
    public void update() {
        List<Link> oldLinks = linkRepository.findOldLinks(OffsetDateTime.now().minusSeconds(updateAge));
        for (Link link : oldLinks) {
            LinkInfo linkInfo = parseUrl(link.getUrl());
            if (linkInfo instanceof GitInfo gitInfo) {
                log.info("Git: " + gitInfo.userName() + "/" + gitInfo.repoName());
                updateFromGutHub(gitInfo.userName(), gitInfo.repoName(), link);

            } else if (linkInfo instanceof StackOverflowInfo stackOverflowInfo) {
                log.info("StackOverflow: " + stackOverflowInfo.id());
                updateFromStackOverflow(stackOverflowInfo.id(), link);
            }
            //В любом случае отмечаем, что ссылку уже посмотрели, даже если это неправильная ссылка
            linkRepository.updateToCurrentDate(link.getId());
        }
    }

    private void updateFromGutHub(String user, String repository, Link link) {
        String description;
        RepoResponse response = gitHubClient.findRepository(user, repository);
        OffsetDateTime lastUpdated = response.updatedAt();
        OffsetDateTime lastPushed = response.pushedAt();

        if (lastUpdated != null && link.getUpdated().isBefore(lastUpdated)) {
            description = "репозиторий был обновлён";
            notifyBot(link, description);
            log.info(link.getUrl() + " " + description);
            log.info(response.toString());
        }
        if (lastPushed != null && link.getUpdated().isBefore(lastPushed)) {
            description = "появились новые коммиты";
            notifyBot(link, description);
            log.info(link.getUrl() + " " + description);
            log.info(response.toString());
        }
    }

    private void updateFromStackOverflow(String questionId, Link link) {
        String description;
        QuestionResponse response = stackOverflowClient.findQuestion(questionId);
        OffsetDateTime lastEditDate = response.items().get(0).lastEditDate();
        OffsetDateTime lastActivityDate = response.items().get(0).lastActivityDate();

        if (lastEditDate != null && link.getUpdated().isBefore(lastEditDate)) {
            description = "вопрос был обновлён";
            notifyBot(link, description);
            log.info(link.getUrl() + " " + description);
            log.info(response.toString());
        }
        if (lastActivityDate != null && link.getUpdated().isBefore(lastActivityDate)) {
            description = "есть активность по вопросу";
            notifyBot(link, description);
            log.info(link.getUrl() + " " + description);
            log.info(response.toString());
        }
    }

    private LinkInfo parseUrl(String url) {
        LinkParser parserChain = new GitLinkParser(new StackOverflowLinkParser(null));
        return parserChain.parseLink(url);
    }

    public void notifyBot(Link link, String description) {
        List<Long> tgChatIds = subscriptionRepository.findAllByLink(link.getId())
                .stream()
                .map(Chat::getId)
                .toList();

        LinkUpdate update = new LinkUpdate(
                link.getId(),
                URI.create(link.getUrl()),
                description,
                tgChatIds
        );
        messageService.send(update);
    }
}
