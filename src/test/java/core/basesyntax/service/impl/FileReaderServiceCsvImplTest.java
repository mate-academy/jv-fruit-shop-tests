package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.ReadFromFileCsvException;
import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderServiceCsvImplTest {
    private static final String FILE_PATH = "src/test/resources/input.csv";
    private static final FileReaderService fileReaderService = new FileReaderServiceCsvImpl();

    @Test
    void readData_invalidPath_notOk() {
        assertThrows(ReadFromFileCsvException.class, () -> {
            fileReaderService.readData("asd.asd");
        });
    }

    @Test
    void readData_ok() {
        List<String> stringsFromFile = fileReaderService.readData(FILE_PATH);
        String expectedFirstString = "\uFEFFtype,fruit,quantity";
        String actualFirstString = stringsFromFile.get(0);
        assertEquals(expectedFirstString, actualFirstString);
    }
}
