package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CsvFileReaderService;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CsvFileReaderImplTest {

    @Test
    public void readFromCsv_validData_Ok() {
        final String valid_input_source_path = "src/test/resources/Information.csv";
        String line1 = "type,fruit,quantity";
        String line2 = "b,banana,20";
        String line3 = "b,apple,100";
        CsvFileReaderService csvFileReaderService = new CsvFileReaderServiceImpl();
        List<String> list = csvFileReaderService.readLineFromFile(valid_input_source_path);
        assertEquals(line1, list.get(0));
        assertEquals(line2, list.get(1));
        assertEquals(line3, list.get(2));
    }

    @Test
    public void readFromCsv_inValidData_NotOk() {
        final String invalid_input_source_path = "__src/test/resources/InvalidInformation.csv";
        final CsvFileReaderService csvFileReaderService = new CsvFileReaderServiceImpl();
        final String nullPath = null;
        final String emptyPath = " ";
        assertThrows(RuntimeException.class, () ->
                        csvFileReaderService.readLineFromFile(invalid_input_source_path),
                "Can't read data from file: ");
        assertThrows(RuntimeException.class, () ->
                        csvFileReaderService.readLineFromFile(nullPath),
                "Can't read data from file: ");
        assertThrows(RuntimeException.class, () ->
                        csvFileReaderService.readLineFromFile(emptyPath),
                "Can't read data from file: ");
    }
}
