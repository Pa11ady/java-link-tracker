package ru.tinkoff.edu.java.linkparser.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.java.linkparser.dto.GitInfo;

import static org.junit.jupiter.api.Assertions.*;

class GitLinkParserTest {
    GitLinkParser gitLinkParser;


    @BeforeEach
    void setUp() {
        gitLinkParser = new GitLinkParser(null);
    }

    @Test
    void parse_linkCorrect_ReturnGitInfo() {
        //given
        String link = "https://github.com/sanyarnd/tinkoff-java-course-2022";

        //when
        GitInfo gitInfo =(GitInfo) gitLinkParser.parseLink(link);

        //then
        assertNotNull(gitInfo);
        assertEquals("sanyarnd", gitInfo.userName());
        assertEquals("tinkoff-java-course-2022", gitInfo.repoName());
    }

    @Test
    void parse_NotLink_ReturnNull() {
        //given
        String link = "12345";

        //when
        GitInfo gitInfo =(GitInfo) gitLinkParser.parseLink(link);

        //then
        assertNull(gitInfo);
    }

    @Test
    void parse_LinkNotGit_ReturnNull() {
        //given
        String link = "https://edu.tinkoff.ru";

        //when
        GitInfo gitInfo =(GitInfo) gitLinkParser.parseLink(link);

        //then
        assertNull(gitInfo);
    }
}