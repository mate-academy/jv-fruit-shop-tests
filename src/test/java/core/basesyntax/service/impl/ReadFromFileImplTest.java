package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFromFileImplTest {
    private static FileReaderService readFromFile;
    private static final String VALID_FILE_TO_PATH = "src/test/resources/Hello.csv";
    private static final String EXPECTED_DATA = "Hello, mate!";

    @BeforeClass
    public static void beforeClass() {
        readFromFile = new ReadFromFileImpl();
    }

    @Test
    public void readFile_isContentEqual_ok() {
        String actualString;
        try {
            actualString = Files.readString(Paths.get(VALID_FILE_TO_PATH));
        } catch (IOException e) {
            throw new FruitShopException("Enter correct file path");
        }
        assertEquals(EXPECTED_DATA, actualString);
    }

    @Test(expected = FruitShopException.class)
    public void readFile_pathIsNull_notOk() {
        readFromFile.readFile(null);
    }

    @Test(expected = FruitShopException.class)
    public void readFile_pathIsIncorrect_notOk() {
        readFromFile.readFile("src/test/resources/Hello.txt");
    }
}
