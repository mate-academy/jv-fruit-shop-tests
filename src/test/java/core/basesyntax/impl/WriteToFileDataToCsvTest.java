package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriteDataToFileService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriteToFileDataToCsvTest {
    private static final String VALID_FILE_NAME = "src/test/resources/test_output.csv";
    private static final String NON_EXISTENT_PATH =
            "src/test/resources/non_existent_directory/output.csv";
    private static final String OUTPUT_DATA = "fruit,quantity\napple,10\nbanana,15";
    private static WriteDataToFileService writeDataToFileService;

    @BeforeAll
    static void setUp() {
        writeDataToFileService = new WriteToCsv();
    }

    @Test
    void write_ValidData_Ok() {
        String report = "Test report data";
        writeDataToFileService.writeToFile(report, VALID_FILE_NAME);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(VALID_FILE_NAME))) {
            String line = bufferedReader.readLine();
            assertEquals(report, line, "Content should match what was written!");
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file: " + VALID_FILE_NAME, e);
        }
    }

    @Test
    public void testWriteToFile_Exception() {
        assertThrows(RuntimeException.class,
                () -> writeDataToFileService.writeToFile(OUTPUT_DATA, NON_EXISTENT_PATH));
    }
}
