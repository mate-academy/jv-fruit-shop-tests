package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.ReaderService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String FROM_FILE = "src/main/resources/input.csv";
    private static final String FROM_EMPTY_FILE = "src/main/resources/empty_input.csv";
    private static final String SEPARATOR = System.lineSeparator();
    private static final String INVALID_PATH = "invalid path";
    private ReaderService readerService;

    @Test
    public void read_validData_ok() {
        String expected = "b,banana,20" + SEPARATOR
                + "b,apple,100" + SEPARATOR
                + "s,banana,100" + SEPARATOR
                + "p,banana,13" + SEPARATOR
                + "r,apple,10" + SEPARATOR
                + "p,apple,20" + SEPARATOR
                + "p,banana,5" + SEPARATOR
                + "s,banana,50" + SEPARATOR;
        try {
            readerService = new CsvFileReaderServiceImpl(
                    new BufferedReader(new FileReader(FROM_FILE)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
        String actual = readerService.read();
        assertEquals("The read method should return: " + expected, expected, actual);
    }

    @Test
    public void read_emptyFile_ok() {
        try {
            readerService = new CsvFileReaderServiceImpl(
                    new BufferedReader(new FileReader(FROM_EMPTY_FILE)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
        boolean actual = readerService.read().isEmpty();
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_invalidPath_notOk() {
        try {
            readerService = new CsvFileReaderServiceImpl(
                    new BufferedReader(new FileReader(INVALID_PATH)));
            readerService.read();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
    }
}
