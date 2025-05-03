package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static FileReaderService service;

    @BeforeClass
    public static void beforeClass() {
        service = new FileReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromDoesNotExistFile_NotOk() {
        service.readFromFile("src/does_not_exist_file.csv");
    }

    @Test
    public void readFromExistFile_Ok() {
        String fileName = "src/test/resources/test_report.csv";
        List<String> expected = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileName))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                expected.add(currentLine);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file: " + fileName, e);
        }
        List<String> actual = service
                .readFromFile(fileName);
        assertEquals(expected, actual);
    }
}
