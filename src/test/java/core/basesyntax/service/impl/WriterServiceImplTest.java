package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    public static final String INPUT_FILE_PATH = "src/test/resources/input.csv";
    public static final String OUTPUT_FILE_PATH = "src/test/resources/output.csv";
    public static final String BASIC_REPORT = "abc" + System.lineSeparator() + "def";
    public static final String FIRST_LINE = "abc";
    public static final String SECOND_LINE = "def";
    public static final String INVALID_FILE_PATH = "";
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void validParams_Ok() throws IOException {
        writerService.writeToFile(OUTPUT_FILE_PATH, BASIC_REPORT);
        List<String> expected = new ArrayList<>();
        expected.add(FIRST_LINE);
        expected.add(SECOND_LINE);
        List<String> actual = Files.readAllLines(Path.of(OUTPUT_FILE_PATH));
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void invalidPath_notOk() {
        writerService.writeToFile(INVALID_FILE_PATH, BASIC_REPORT);
    }

    @Test (expected = RuntimeException.class)
    public void nullPath_notOk() {
        writerService.writeToFile(null, BASIC_REPORT);
    }

    @Test (expected = RuntimeException.class)
    public void nullReport_notOk() {
        writerService.writeToFile(INPUT_FILE_PATH, null);
    }
}
