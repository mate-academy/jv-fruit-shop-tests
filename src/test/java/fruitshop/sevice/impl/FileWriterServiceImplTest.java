package fruitshop.sevice.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.sevice.FileWriterService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private FileWriterService fileWriterService = new FileWriterServiceImpl();

    @Test
    void writeToFile_invalidDirectory_notOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,40";
        assertThrows(RuntimeException.class,
                () -> fileWriterService.writeToFile(report, "src/test/resources/.."));
    }

    @Test
    void writeToFile_validDirectory_ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,40"
                + System.lineSeparator();
        assertDoesNotThrow(
                () -> fileWriterService.writeToFile(report, "src/test/resources/report.csv"));
        assertEquals(report, readFromTestFile("src/test/resources/report.csv"));
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

