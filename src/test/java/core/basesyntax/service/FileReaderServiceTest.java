package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileReaderServiceTest {
    @Test(expected = RuntimeException.class)
    public void readFromDoesNotExistFile_NotOk() {
        new FileReaderServiceImpl().readFromFile("src/does_not_exist_file.csv");
    }

    @Test
    public void readFromExistFile_Ok() {
        List<String> expected = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader("src/test/resources/test_report.csv"))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                expected.add(currentLine);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file");
        }
        List<String> actual = new FileReaderServiceImpl()
                .readFromFile("src/test/resources/test_report.csv");
        assertEquals(expected, actual);
    }
}
