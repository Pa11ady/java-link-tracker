package ru.tinkoff.edu.java.scrapper.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.github.dto.RepoResponse;

@Component
public class GitHubClientImpl implements GitHubClient {
    private final WebClient webClient;

    @Autowired
    public GitHubClientImpl(@Qualifier("webClientGit") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public RepoResponse findRepository(String userName, String repoName) {
        return webClient
                .get()
                .uri("/repos/{owner}/{repo}", userName, repoName)
                .retrieve()
                .bodyToMono(RepoResponse.class)
                .block();
    }
}
