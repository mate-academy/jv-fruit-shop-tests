package core.basesyntax.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileWriterServiceTest {
    private static final String FILE_NAME = "src/test/java/resources/testreport.csv";
    private static FileWriterService writerService;

    @BeforeClass
    public static void beforeAll() {
        writerService = new FileWriterServiceImpl();
    }

    @Test
    public void writeFile_ok() {
        String message = "fruit,quantity" + System.lineSeparator()
                + "apple,65" + System.lineSeparator()
                + "banana,62" + System.lineSeparator()
                + "orange,1082" + System.lineSeparator()
                + "grapefruit,6";
        writerService.writeFile(message, FILE_NAME);
        try {
            List<String> actual = Files.readAllLines(Path.of(FILE_NAME));
            List<String> expected = List.of(
                    "fruit,quantity",
                    "apple,65",
                    "banana,62",
                    "orange,1082",
                    "grapefruit,6");
            Assert.assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file from path: " + FILE_NAME, e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeExistingFile_shouldThrowRuntimeException_notOk() throws IOException {
        Files.createFile(Path.of(FILE_NAME));
        writerService.writeFile("sth", FILE_NAME);
    }

    @After
    public void afterEach() throws IOException {
        Files.delete(Path.of(FILE_NAME));
    }

}