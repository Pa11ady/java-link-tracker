package ru.tinkoff.edu.java.linkparser.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowInfo;

import static org.junit.jupiter.api.Assertions.*;

class StackOverflowLinkParserTest {
    StackOverflowLinkParser parser;

    @BeforeEach
    void setUp() {
        parser = new StackOverflowLinkParser(null);
    }

    @Test
    void parse_linkCorrect_ReturnInfo() {
        //given
        String link = "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";

        //when
        StackOverflowInfo info = (StackOverflowInfo) parser.parseLink(link);

        //then
        assertNotNull(info);
        assertEquals("1642028", info.id());
    }

    @Test
    void parse_GoggleLink_ReturnNull() {
        //given
        String link = "https://google.com";

        //when
        StackOverflowInfo info = (StackOverflowInfo) parser.parseLink(link);

        //then
        assertNull(info);
    }

    @Test
    void parse_NotLink_ReturnNull() {
        //given
        String link = "Test";

        //when
        StackOverflowInfo info = (StackOverflowInfo) parser.parseLink(link);

        //then
        assertNull(info);
    }
}