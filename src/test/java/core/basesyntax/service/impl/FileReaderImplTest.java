package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    public static final String WRONG_FILEPATH = "/var/lib";
    private static FileReader readerService;
    private static final List<String> ONE_LINE_TEXT = new ArrayList<>();
    private static final List<String> SOME_LINES_TEXT = new ArrayList<>();
    private static final String ONE_LINE_TEXT_PATH =
            "src/test/resources/oneLineText.csv";
    private static final String SOME_LINES_TEXT_PATH =
            "src/test/resources/someLinesText.csv";

    @BeforeClass
    public static void beforeClass() {
        readerService = new FileReaderImpl();
        ONE_LINE_TEXT.add("This file have only one line." + System.lineSeparator());
        SOME_LINES_TEXT.add("This file have some lines." + System.lineSeparator()
                + "This file have some lines!" + System.lineSeparator());
    }

    @Test
    public void readFileWithOneLine_Ok() {
        List<String> actual = readerService.readData(ONE_LINE_TEXT_PATH);
        assertEquals(ONE_LINE_TEXT, actual);
        assertEquals(1, actual.size());
    }

    @Test
    public void readFileWithSomeLines_Ok() {
        List<String> actual = readerService.readData(SOME_LINES_TEXT_PATH);
        assertEquals(SOME_LINES_TEXT, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_wrongPath_NotOk() {
        readerService.readData(WRONG_FILEPATH);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullPath_NotOk() {
        readerService.readData(null);
    }
}
