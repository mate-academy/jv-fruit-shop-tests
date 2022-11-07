package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileWriterServiceImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/test_file.csv";
    private static final String VALID_DATA = "some text 1" + System.lineSeparator()
             + "some text 2" + System.lineSeparator() + "some text 3";
    private static final String EMPTY_DATA = "";
    private FileWriterService fileWriterService;

    @Before
    public void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_validData_ok() {
        fileWriterService.writeToFile(VALID_DATA, VALID_FILE_PATH);
        List<String> expected = List.of(VALID_DATA.split(System.lineSeparator()));
        List<String> actual = readFromFile(Path.of(VALID_FILE_PATH));
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullFilePath_notOk() {
        fileWriterService.writeToFile(VALID_DATA, null);
    }

    @Test
    public void writeToFile_emptyReport_ok() {
        fileWriterService.writeToFile(EMPTY_DATA, VALID_FILE_PATH);
        List<String> expected = Collections.emptyList();
        List<String> actual = readFromFile(Path.of(VALID_FILE_PATH));
        assertEquals(expected, actual);
    }

    private List<String> readFromFile(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
