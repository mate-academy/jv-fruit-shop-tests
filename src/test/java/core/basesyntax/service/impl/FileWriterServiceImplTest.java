package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService writerService;
    private static final String DEFAULT_REPORT = "report";
    private static final String OUTPUT_PATH = "src/test/resources/output.csv";
    private static final String HEADER = "fruit,balance";

    @BeforeClass
    public static void setUp() {
        writerService = new FileWriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void write_emptyFilePathString_NotOk() {
        writerService.writeToFile("", DEFAULT_REPORT);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullFilePath_NotOk() {
        writerService.writeToFile(null, DEFAULT_REPORT);
    }

    @Test (expected = NullPointerException.class)
    public void write_nullReport_NotOk() {
        writerService.writeToFile(OUTPUT_PATH, null);
    }

    @Test
    public void write_validInput_Ok() {
        String report = HEADER + System.lineSeparator()
                + "banana,180" + System.lineSeparator()
                + "apple,10" + System.lineSeparator();
        writerService.writeToFile(OUTPUT_PATH, report);
        List<String> actual = readFromFile(OUTPUT_PATH);
        List<String> expected = List.of(HEADER,"banana,180", "apple,10");
        assertEquals(expected, actual);
    }

    @Test
    public void write_emptyReport_Ok() {
        writerService.writeToFile(OUTPUT_PATH, "");
        List<String> actual = readFromFile(OUTPUT_PATH);
        List<String> expected = Collections.EMPTY_LIST;
        assertEquals(expected, actual);
    }

    private List<String> readFromFile(String fileName) {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can`t read data in test");
        }
    }
}
