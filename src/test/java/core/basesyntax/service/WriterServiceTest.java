package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static WriterService writerService;

    @BeforeClass
    public static void init() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_Ok() {
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

    @Test (expected = RuntimeException.class)
    public void writeToNonExistentFile_NotOk() {
        writerService.writeToFile("test", "");
    }
}
