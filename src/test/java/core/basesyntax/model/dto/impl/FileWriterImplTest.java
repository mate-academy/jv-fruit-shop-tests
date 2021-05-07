package core.basesyntax.model.dto.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static final String FILE_NAME = "TestFile.csv";
    private static final String REPORT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,103"
            + System.lineSeparator()
            + "apple,90"
            + System.lineSeparator();
    private static FileWriterImpl fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void fileWriteTest_Ok() {
        fileWriter.writeToFile(REPORT, FILE_NAME);
        List<String> expected = Arrays.asList(REPORT.split(System.lineSeparator()));
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("File not found or can't be read", e);
        }
        Assertions.assertEquals(expected, actual);
    }
}
