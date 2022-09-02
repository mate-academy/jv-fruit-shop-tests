package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String TEST_FILE_NAME = "src/test/resources/test_writer.csv";
    private static final String EMPTY_FILE_NAME = "src/test/resources/empty_file.csv";
    private static WriterService writerService;

    private static String getTestReport() {
        return "fruit,quantity,banana,1,apple,1";
    }

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void create_validFile_isOk() {
        String expected = "test report";
        writerService.writeToFile(expected, TEST_FILE_NAME);
        String actual;
        try {
            actual = Files.readString(Path.of(TEST_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from file " + TEST_FILE_NAME);
        }
        assertEquals("Wrong data", expected, actual);
    }

    @Test
    public void create_emptyFile_isOk() {
        writerService.writeToFile("", EMPTY_FILE_NAME);
        String actual = readFromFile();
        assertEquals("Invalid data", "", actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_fileNameIsNull_isNotOk() {
        writerService.writeToFile(null, getTestReport());
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_reportIsNull_isNotOk() {
        writerService.writeToFile(TEST_FILE_NAME, null);
    }

    private String readFromFile() {
        String lines;
        try {
            lines = Files.readString(Path.of(EMPTY_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from file " + EMPTY_FILE_NAME);
        }
        return lines;
    }
}
