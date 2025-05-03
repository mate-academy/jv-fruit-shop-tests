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
    private static final String VALID_FILE_PATH = "src/test/resources/writer_test_file.csv";
    private static final String VALID_DATA = "some text 1" + System.lineSeparator()
             + "some text 2" + System.lineSeparator() + "some text 3";
    private static final String EMPTY_DATA = "";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_validData_ok() {
        fileWriterService.writeToFile(VALID_DATA, VALID_FILE_PATH);
        List<String> expected = List.of(VALID_DATA.split(System.lineSeparator()));
        List<String> actual = readFromFile(VALID_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_emptyReport_ok() {
        fileWriterService.writeToFile(EMPTY_DATA, VALID_FILE_PATH);
        List<String> expected = Collections.emptyList();
        List<String> actual = readFromFile(VALID_FILE_PATH);
        assertEquals(expected, actual);
    }

    private List<String> readFromFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
