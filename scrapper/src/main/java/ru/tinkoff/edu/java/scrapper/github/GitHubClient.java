package ru.tinkoff.edu.java.scrapper.github;

import ru.tinkoff.edu.java.scrapper.github.dto.RepoResponse;

public interface GitHubClient {
    RepoResponse findRepository(String userName, String repoName);
}
