package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.FileReaderException;
import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String ONE_LINE_TEXT_PATH =
            "src/test/resources/one-line-text.csv";
    private static final String SOME_LINES_TEXT_PATH =
            "src/test/resources/some-lines-with-text.csv";
    private static final String INVALID_PATH = "invalid.cvs";
    private static final List<String> ONE_LINE_TEXT = new ArrayList<>();
    private static final List<String> SOME_LINES_TEXT = new ArrayList<>();
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
        ONE_LINE_TEXT.add("This file has only one line");
        SOME_LINES_TEXT.add("This file has 3 lines");
        SOME_LINES_TEXT.add("This is the second line");
        SOME_LINES_TEXT.add("This is the third line");
    }

    @Test
    public void readFileWithOneLine_Ok() {
        List<String> actual = fileReaderService.readFromFile(ONE_LINE_TEXT_PATH);
        assertEquals(ONE_LINE_TEXT, actual);
        assertEquals(1, actual.size());
    }

    @Test
    public void readFileWithSomeLines_Ok() {
        List<String> actual = fileReaderService.readFromFile(SOME_LINES_TEXT_PATH);
        assertEquals(SOME_LINES_TEXT, actual);
        assertEquals(3, actual.size());
    }

    @Test(expected = FileReaderException.class)
    public void readFileWithNullPath_NotOk() {
        fileReaderService.readFromFile(null);
    }

    @Test(expected = FileReaderException.class)
    public void readFileWithInvalidPath_NotOk() {
        fileReaderService.readFromFile(INVALID_PATH);
    }
}
