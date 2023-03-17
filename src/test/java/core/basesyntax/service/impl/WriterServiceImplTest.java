package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {

    private static final String TEST_FILE_PATH = "./src/main/resources/testReport.csv";
    private static String content;
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeOneLineValidDataOk() {
        content = "Hello mates!";
        writerService.write(TEST_FILE_PATH, content);
        assertEquals(content, read());
    }

    @Test
    void writeThreeLineValidDataOk() {
        content = "Hello mates!" + System.lineSeparator()
                + "how it is going?" + System.lineSeparator()
                + "All great";
        writerService.write(TEST_FILE_PATH, content);
        assertEquals(content, read());
    }

    @Test
    void writeDigitsAndSymbolsOk() {
        content = "1234321.,()[]";
        writerService.write(TEST_FILE_PATH, content);
        assertEquals(content, read());
    }

    @Test
    void writeEmptyStringOk() {
        content = "";
        writerService.write(TEST_FILE_PATH, content);
        assertEquals(content, read());
    }

    @Test
    void writeToNullNotOk() {
        content = "Hello mates!";
        assertThrows(RuntimeException.class, () -> {
            writerService.write(null, content);
        });
    }

    @AfterEach
    void tearDown() {
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
