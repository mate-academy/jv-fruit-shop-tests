package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import core.basesyntax.storage.Storage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;

    @BeforeAll
    static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void writer_ValidReportFile_Ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        assertDoesNotThrow(() -> fileWriterService
                 .writeToFile(report,"src/test/resources/validReportFile.csv"));
    }

    @Test
    void writer_NonExistingReportFile_notOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        String actual = "src/test/resources///";
        assertThrows(
                RuntimeException.class, () -> fileWriterService.writeToFile(report, actual));
    }

    @Test
    void writer_WriteToFile_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana, 152" + System.lineSeparator()
                + "apple, 90" + System.lineSeparator();
        fileWriterService.writeToFile(expected, "src/test/resources/validReportFile.csv");
        String actual = readFromFile("src/test/resources/validReportFile.csv");
        assertEquals(expected, actual);

    }

    private String readFromFile(String fromFileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fromFileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
