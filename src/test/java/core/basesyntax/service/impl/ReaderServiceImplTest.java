package core.basesyntax.service.impl;

import core.basesyntax.servise.ReaderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static final String FROM_FILE = "src/test/resourcesfortest/InputFile.csv";

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void read_pathFileIsNullNotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(null);
        }, "Path to file can't be null");
    }

    @Test
    public void read_pathEqualsInputPath() {
        String actual = "src/test/resourcesfortest/InputFile.csv";
        Assertions.assertEquals(FROM_FILE, actual);
    }

    @Test
    public void read_pathNotEqualsInputPath() {
        String actual = "src/test/resourcesfortest/sdw.csv";
        Assertions.assertThrows(RuntimeException.class,() -> {
            readerService.readFromFile(actual);
        },"This path not exist");
    }

    @Test
    public void read_FileIsNotEmptyOk() {
        Assertions.assertTrue(readerService.readFromFile(FROM_FILE).size() > 0,
                "File is not empty");
    }

    @Test
    public void read_FileIsEmptyNotOk() {
        String pathToFile = "src/test/resourcesfortest/file.csv";
        Assertions.assertTrue(readerService.readFromFile(pathToFile).isEmpty());
    }
}
