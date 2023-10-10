package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileWriteServiceImpl;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriteServiceImplTest {
    private static FileWriteService fileWriteService;

    @BeforeAll
    static void beforeAll() {
        fileWriteService = new FileWriteServiceImpl();
    }

    @Test
    void writeToFile_inInvalidDirectory_notOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,40";
        assertThrows(RuntimeException.class,
                () -> fileWriteService.writeToFile(report, "src/resources/text.txt"));
    }

    @Test
    void writeToFile_validDirectory_ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,40"
                + System.lineSeparator();
        assertDoesNotThrow(
                () -> fileWriteService.writeToFile(report, "src/main/resources/report.txt"));
        assertEquals(report, readFromTestFile("src/main/resources/report.txt"));
    }

    private String readFromTestFile(String fromFileName) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fromFileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }
}
