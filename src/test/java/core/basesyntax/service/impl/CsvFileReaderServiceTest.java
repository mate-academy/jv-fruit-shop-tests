package core.basesyntax.service.impl;

import core.basesyntax.exceptions.ReadDataFromFileException;
import core.basesyntax.service.FileReaderService;
import java.nio.file.FileSystems;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final FileReaderService csvFileReader = new CsvFileReaderService();
    private static final String SOURCE_FILE_WITHOUT_DATA = FileSystems.getDefault()
            .getPath("src/test/resources/emptyDataTest.csv").toString();
    private static final String EXISTING_SOURCE_FILE = FileSystems.getDefault()
            .getPath("src/test/resources/dataTest.csv").toString();
    private static final String NOT_EXISTING_SOURCE_FILE = FileSystems.getDefault()
            .getPath("src/test/resources/dataTest1.csv").toString();
    private static final String TEXT_IN_SOURCE_FILE = "type,fruit,quantity" + LINE_SEPARATOR
                                + "b,banana,20" + LINE_SEPARATOR
                                + "b,apple,100" + LINE_SEPARATOR
                                + "s,banana,100";

    @Test
    void readDataFromFile_emptyFile_ok() {
        String actual = csvFileReader.readDataFromFile(SOURCE_FILE_WITHOUT_DATA);
        Assertions.assertEquals("", actual);
    }

    @Test
    void readDataFromFile_existingFile_ok() {
        String actual = csvFileReader.readDataFromFile(EXISTING_SOURCE_FILE);
        Assertions.assertEquals(TEXT_IN_SOURCE_FILE, actual);
    }

    @Test
    void readDataFromFile_notExistingFile_notOk() {
        ReadDataFromFileException exception = null;
        try {
            csvFileReader.readDataFromFile(NOT_EXISTING_SOURCE_FILE);
        } catch (ReadDataFromFileException e) {
            exception = e;
        }
        String expected = "Can't read data from file " + NOT_EXISTING_SOURCE_FILE;
        Assertions.assertEquals(expected, exception.getMessage());
    }
}
