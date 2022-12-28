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
    public void read_pathFileIsNullNotOk() {
        assertThrows("Path to file can't be null", RuntimeException.class, () -> {
            readerService.readFromFile(null);
        });
    }

    @Test
    public void read_pathEqualsInputPath() {
        String actual = "src/test/resources/InputFile.csv";
        assertEquals(FROM_FILE, actual);
    }

    @Test
    public void read_pathNotEqualsInputPath() {
        String actual = "src/test/resources/sdw.csv";
        assertThrows("This path not exist",
                RuntimeException.class,() -> {
                readerService.readFromFile(actual);
            });
    }

    @Test
    public void read_FileIsNotEmptyOk() {
        assertTrue("File is not empty",
                readerService.readFromFile(FROM_FILE).size() > 0);
    }

    @Test
    public void read_FileIsEmptyNotOk() {
        String pathToFile = "src/test/resources/file.csv";
        assertTrue(readerService.readFromFile(pathToFile).isEmpty());
    }
}
