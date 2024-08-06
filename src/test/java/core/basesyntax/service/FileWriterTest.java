package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final String FILE_PATH = "src/test/resources/finalReport.csv";
    private static final String HEADER = "fruit,quantity" + System.lineSeparator();
    private static Writer writer;

    @BeforeAll
    static void beforeAll() {
        writer = new FileWriterImpl();
    }

    @Test
    void writeToFile_writeCorrectDataToFile_ok() throws IOException {
        String content = HEADER + "banana,107" + System.lineSeparator() + "apple,425";
        writer.writeToFile(content, FILE_PATH);
        List<String> expected = Arrays.asList(
                "fruit,quantity",
                "banana,107",
                "apple,425"
        );
        List<String> actual = Files.readAllLines(Path.of(FILE_PATH));
        assertEquals(expected, actual);
    }

    @Test
    void writeToFile_writeNullDataToFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> writer.writeToFile(null, FILE_PATH));
    }

    @Test
    void writeToFile_writeDataToNullFile_notOk() {
        String content = HEADER + "banana,107" + System.lineSeparator() + "b,apple,425";
        assertThrows(RuntimeException.class,
                () -> writer.writeToFile(content, null));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(FILE_PATH));
    }
}
