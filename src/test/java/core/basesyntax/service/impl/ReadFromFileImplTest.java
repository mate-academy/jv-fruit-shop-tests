package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.FruitShopException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFromFileImplTest {
    private static final ReadFromFileImpl readFromFile = new ReadFromFileImpl();
    private static final String path = "src/test/resources/Hello.csv";
    private static final String expectedString = "Hello, mate!";

    @BeforeClass
    public static void beforeClass() {
        try {
            Files.writeString(Paths.get(path), expectedString);
        } catch (IOException e) {
            throw new RuntimeException("Check the file path " + path, e);
        }
    }

    @Test
    public void readFile_isContentEqual_Ok() {
        String actualString;
        try {
            actualString = Files.readString(Paths.get(path));
        } catch (IOException e) {
            throw new FruitShopException("Enter correct file path");
        }
        boolean isContentEqual = actualString.equals(expectedString);
        assertTrue(isContentEqual);
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
