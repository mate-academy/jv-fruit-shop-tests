package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileWriterService;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static final String REPORT = "banana,100" + System.lineSeparator() + "apple,140";
    private static final String FILE_PATH = "src/main/resources/storeToFile.csv";
    private final CsvFileWriterService fileWriterService = new CsvFileWriterServiceImpl();

    @Test
    public void writeFile_Ok() {
        fileWriterService.writeToFile(FILE_PATH, REPORT);
    }

    @Test(expected = RuntimeException.class)
    public void checkFileExistence() {
        String fakeFilePath = "";
        fileWriterService.writeToFile(fakeFilePath, REPORT);
    }
}
