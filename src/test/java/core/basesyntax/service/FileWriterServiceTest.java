package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static FileWriterService writerService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_Ok() {
        String expected = "Hello, this is test sentence.";
        writerService.writeToFile(expected,
                "src/test/resources/test_report.csv");
        String actual = "";
        try {
            actual = String.join("",
                    Files.readAllLines(Path.of("src/test/resources/test_report.csv")));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file.", e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToInvalidFilePath_NotOk() {
        writerService.writeToFile("Hello", "/");
    }
}
