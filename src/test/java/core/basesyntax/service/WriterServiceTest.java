package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterServiceTest {
    private static WriterService writerService;

    @BeforeAll
    public static void init() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_Ok() {
        String actual = "test";
        String fileName = "src/test/java/core/basesyntax/recourses/output-test.csv";
        writerService.writeToFile(actual, fileName);
        String expected;
        try {
            expected = String.join("", Files.readAllLines(Path.of(fileName)));
        } catch (IOException e) {
            throw new RuntimeException("Cant read file for test" + fileName, e);
        }
        assertEquals(expected, actual);
    }

    @Test
    void writeToNonExistentFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile("test", ""));
    }
}
