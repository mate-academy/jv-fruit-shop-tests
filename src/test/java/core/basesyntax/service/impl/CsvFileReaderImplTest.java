package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CsvFileReaderService;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CsvFileReaderImplTest {
    private final CsvFileReaderService csvFileReaderService = new CsvFileReaderServiceImpl();

    @Test
    public void readFromCsv_validData_Ok() {
        final String validFilePath = "src/test/resources/Information.csv";
        String line1 = "type,fruit,quantity";
        String line2 = "b,banana,20";
        String line3 = "b,apple,100";
        List<String> result = csvFileReaderService.readLineFromFile(validFilePath);
        assertEquals(line1, result.get(0));
        assertEquals(line2, result.get(1));
        assertEquals(line3, result.get(2));
    }

    @Test
    public void readFromCsv_notValidData_NotOk() {
        final String invalidFilePath = "__src/test/resources/InvalidInformation.csv";
        final String nullPath = null;
        final String emptyPath = " ";
        assertThrows(RuntimeException.class, () ->
                        csvFileReaderService.readLineFromFile(invalidFilePath),
                "Can't read data from file: ");
        assertThrows(RuntimeException.class, () ->
                        csvFileReaderService.readLineFromFile(nullPath),
                "Can't read data from file: ");
        assertThrows(RuntimeException.class, () ->
                        csvFileReaderService.readLineFromFile(emptyPath),
                "Can't read data from file: ");
    }
}
