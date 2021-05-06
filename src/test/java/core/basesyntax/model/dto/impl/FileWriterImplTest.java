package core.basesyntax.model.dto.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
        List<String> actual = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                actual.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("File not found or can't be read", e);
        }
        Assertions.assertEquals(expected, actual);
    }
}
