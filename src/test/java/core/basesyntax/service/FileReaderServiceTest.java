package core.basesyntax.service;

import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void pathToFile_NullNotOk() {
        fileReaderService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void pathToFile_NotOk() {
        String pathToFile = "fruits";
        fileReaderService.readFromFile(pathToFile);
    }
}
