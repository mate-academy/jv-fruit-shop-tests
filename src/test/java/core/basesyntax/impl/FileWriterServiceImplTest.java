package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static final String RESULT_FILE_PATH = "test.txt";
    private static final String INVALID_RESULT_FILE_PATH = "/invalid/path/test.txt";
    private static final String TEST_DATA = "Hello World!";

    @Before
    public void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @After
    public void clearResults() throws Exception {
        Files.deleteIfExists(Path.of(RESULT_FILE_PATH));
    }

    @Test
    public void writeToFile_writeDataInToFilePath_Ok() {
        //given
        String expected = TEST_DATA;

        //when
        fileWriterService.writeToFile(expected, RESULT_FILE_PATH);
        String actual = readFromFile(RESULT_FILE_PATH).trim();

        //then
        assertEquals("Incorrect data after writing operation: ",
                expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_writeToInvalidPath_NotOk() {
        //when
        fileWriterService.writeToFile(TEST_DATA, INVALID_RESULT_FILE_PATH);

        //then
        fail("Writing to an invalid data path is not allowed.");
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }
}
