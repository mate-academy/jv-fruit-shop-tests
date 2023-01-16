package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileReaderService;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    //1. file path is wrong another from src/test/resources/
    //2. file path is wrong src/test/resources/no file
    private static CsvFileReaderService fileReaderService;
    private static final String VALID_FILE_PATH = "src/test/resources/file.csv";

    @BeforeClass
    public static void setUp() {
        fileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test
    public void fileReader_filePathRight_Ok() {
        Assert.assertEquals(VALID_FILE_PATH, "src/test/resources/file.csv");
    }

    @Test (expected = RuntimeException.class)
    public void fileReader_invalidFilePath_NotOk() {
        fileReaderService.readFromFile(Path.of("src/test/resources/fl.csv"));
    }

    @Test (expected = NullPointerException.class)
    public void fileReader_nullFilePath_NotOk() {
        fileReaderService.readFromFile(Path.of(null));
    }
}
