package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileReaderService;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String FILE_PATH = "src/main/resources/readFromFile.csv";
    private final CsvFileReaderService fileReaderService = new CsvFileReaderServiceImpl();

    @Test
    public void readFile_Ok() {
        fileReaderService.readFile(FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void checkFileExistence_Ok() {
        String fakeFilePath = "hola";
        fileReaderService.readFile(fakeFilePath);
    }
}
