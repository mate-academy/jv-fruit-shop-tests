package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.ReaderService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String FROM_FILE = "src/main/resources/input.csv";
    private static final String FROM_EMPTY_FILE = "src/main/resources/empty_input.csv";
    private ReaderService readerService;

    @Test
    public void read_validData_ok() {
        String expected = "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13" + System.lineSeparator()
                + "r,apple,10" + System.lineSeparator()
                + "p,apple,20" + System.lineSeparator()
                + "p,banana,5" + System.lineSeparator()
                + "s,banana,50" + System.lineSeparator();
        String actual = read(FROM_FILE);
        assertEquals("The read method should return: " + expected, expected, actual);
    }

    @Test
    public void read_emptyFile_ok() {
        boolean actual = read(FROM_EMPTY_FILE).isEmpty();
        assertTrue(actual);
    }

    private String read(String fileName) {
        try {
            readerService = new FileReaderServiceImpl(new BufferedReader(new FileReader(fileName)));
            return readerService.read();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
    }
}
