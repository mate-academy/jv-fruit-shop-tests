package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static final String VALID_PATH = "src/test/resources/Bye.csv";
    private static final String DEFAULT_REPORT = "See soon";

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_contentEqual_ok() {
        fileWriterService.writeToFile(DEFAULT_REPORT, VALID_PATH);
        String actual;
        try {
            actual = Files.readString(Paths.get(VALID_PATH));
        } catch (IOException e) {
            throw new FruitShopException("Writing to file is incorrect");
        }
        assertEquals(DEFAULT_REPORT, actual);
    }

    @Test(expected = FruitShopException.class)
    public void writeToFile_addInvalidPath_notOk() {
        fileWriterService.writeToFile(DEFAULT_REPORT, "");
    }

    @Test(expected = FruitShopException.class)
    public void writeToFile_addEmptyReport_notOk() {
        fileWriterService.writeToFile("", VALID_PATH);
    }

    @Test(expected = FruitShopException.class)
    public void writeToFileWithNullParameters_notOk() {
        fileWriterService.writeToFile(null, null);
    }
}
