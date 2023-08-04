package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.WriteDataToFileService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriteToFileDataToCsvTest {
    private static WriteDataToFileService writeDataToFileService;

    @BeforeAll
    static void setUp() {
        writeDataToFileService = new WriteToFileDataToCsv();
    }

    @Test
    void write_ValidData_Ok() {
        String report = "Test report data";
        String fileName = "src/test/resources/test_output.csv";
        writeDataToFileService.writeToFile(report, fileName);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufferedReader.readLine();
            assertEquals(report, line, "Content should match what was written!");
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file: " + fileName, e);
        }
    }
}
