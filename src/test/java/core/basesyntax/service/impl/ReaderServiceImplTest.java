package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.servise.ReaderService;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static final String FROM_FILE = "src/test/resources/InputFile.csv";

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_FilePathNull_notOk() {
        assertThrows("Path to file can't be null", RuntimeException.class, () -> {
            readerService.readFromFile(null);
        });
    }

    @Test
    public void readFromFile_pathEqualsInputPath_Ok() {
        String actual = "src/test/resources/InputFile.csv";
        assertEquals(FROM_FILE, actual);
    }

    @Test
    public void readFromFile_pathNotEqualsInputPath_notOk() {
        String actual = "src/test/resources/sdw.csv";
        assertThrows("This path not exist",
                RuntimeException.class, () -> {
                readerService.readFromFile(actual);
            });
    }

    @Test
    public void readFromFile_FileNotEmpty_Ok() {
        assertTrue("File is not empty",
                readerService.readFromFile(FROM_FILE).size() > 0);
    }

    @Test
    public void readFromFile_FileIsEmpty_notOk() {
        String pathToFile = "src/test/resources/file.csv";
        assertTrue(readerService.readFromFile(pathToFile).isEmpty());
    }
}
