package service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Test;
import service.WriterService;

public class WriterServiceImplTest {
    private static final String TEST_FILE_PATH = "./src/test/resources/testReport.csv";
    private static String content;
    private static WriterService writerService = new WriterServiceImpl();

    @Test
    public void writeOneLineValidDataOk() {
        content = "Hello mates!";
        writerService.write(TEST_FILE_PATH, content);
        assertEquals(content, read());
    }

    @Test
    public void writeThreeLineValidDataOk() {
        content = "Hello mates!" + System.lineSeparator()
                + "how it is going?" + System.lineSeparator()
                + "All great";
        writerService.write(TEST_FILE_PATH, content);
        assertEquals(content, read());
    }

    @Test
    public void writeDigitsAndSymbolsOk() {
        content = "1234321.,()[]";
        writerService.write(TEST_FILE_PATH, content);
        assertEquals(content, read());
    }

    @Test
    public void writeEmptyStringOk() {
        content = "";
        writerService.write(TEST_FILE_PATH, content);
        assertEquals(content, read());
    }

    @Test (expected = RuntimeException.class)
    public void writeToNullNotOk() {
        content = "Hello mates!";
        writerService.write(null, content);
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Path.of(TEST_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("can't delete this file " + TEST_FILE_PATH);
        }
    }

    private String read() {
        try {
            return Files.readString(Path.of(TEST_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("can't read this content " + content
                    + " to this file " + TEST_FILE_PATH);
        }
    }
}
