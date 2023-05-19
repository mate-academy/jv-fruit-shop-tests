package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

public class CsvFileWriterServiceImplTest {
    @Test
    public void writeToFile_validData_Ok() {
        final String validPath = "src/test/resources/report.csv";
        final String line1 = "fruit,quantity";
        final String line2 = "banana,152";
        final String line3 = "apple,90";
        final CsvFileWriterServiceImpl csvFileWriterService = new CsvFileWriterServiceImpl();
        String data = line1 + System.lineSeparator()
                + line2 + System.lineSeparator()
                + line3 + System.lineSeparator();
        csvFileWriterService.writeToFile(data, validPath);
        List<String> linesFromFile = new CsvFileReaderServiceImpl().readLineFromFile(validPath);
        assertEquals(List.of(line1,line2,line3), linesFromFile);
    }

    @Test
    public void writeToFile_inValidData_NotOk() {
        final String invalidPath = "src/test/resources/invalid_path/report.csv";
        final String nullPath = null;
        final String emptyPath = "";
        final CsvFileWriterServiceImpl csvFileWriterService = new CsvFileWriterServiceImpl();
        String data = "fruit,quantity";
        assertThrows(RuntimeException.class, () ->
                        csvFileWriterService.writeToFile(data, invalidPath),
                "Can't write data to file: ");
        assertThrows(RuntimeException.class, () ->
                        csvFileWriterService.writeToFile(data, nullPath),
                "Can't write data to file: ");
        assertThrows(RuntimeException.class, () ->
                        csvFileWriterService.writeToFile(data, emptyPath),
                "Can't write data to file: ");
    }
}
