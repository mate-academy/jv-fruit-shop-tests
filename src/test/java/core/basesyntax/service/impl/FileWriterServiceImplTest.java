package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService = new FileWriterServiceImpl();
    private static final String validPath = "src/test/resources/Bye.csv";
    private static final String stringToReport = "See soon";

    @Test
    public void writeToFile_isContentEqual_Ok() {
        fileWriterService.writeToFile(stringToReport, validPath);
        String actual;
        try {
            actual = Files.readString(Paths.get(validPath));
        } catch (IOException e) {
            throw new FruitShopException("Writing to file is incorrect");
        }
        boolean isEqualString = actual.equals(stringToReport);
        assertTrue("Writing to file is correct", isEqualString);
    }

    @Test(expected = FruitShopException.class)
    public void writeToFile_addInvalidPath_notOk() {
        fileWriterService.writeToFile(stringToReport, "");
    }

    @Test(expected = FruitShopException.class)
    public void writeToFile_addEmptyReport_notOk() {
        fileWriterService.writeToFile("", validPath);
    }

    @Test(expected = FruitShopException.class)
    public void writeToFileWithNullParameters_notOk() {
        fileWriterService.writeToFile(null, null);
    }
}
