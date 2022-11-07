package core.basesyntax.service.impl;

import static org.junit.Assert.fail;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader reader;
    private static final String INPUT_PATH_TOFILE = "src/test/resources/input.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/emptyFileForTest.csv";

    @BeforeClass
    public static void createFileReader() {
        reader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20",
                "p,banana,5", "s,banana,50");
        List<String> actual = reader.readFromFile(INPUT_PATH_TOFILE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFromFileWrongName_notOk() {
        String wrongPath = INPUT_PATH_TOFILE + "L";
        try {
            List<String> actual = reader.readFromFile(wrongPath);
        } catch (Exception e) {
            Assert.assertEquals("Can't read data from file " + wrongPath, e.getMessage());
            return;
        }
        fail("Expected validation exception was not thrown");
    }

    @Test
    public void readFromFileEmptyName_notOk() {
        String wrongPath = "";
        try {
            List<String> actual = reader.readFromFile(wrongPath);
        } catch (Exception e) {
            Assert.assertEquals("Can't read data from file " + wrongPath, e.getMessage());
            return;
        }
        fail("Expected validation exception was not thrown");
    }

    @Test
    public void readFromEmptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = reader.readFromFile(PATH_TO_EMPTY_FILE);
        Assert.assertEquals(expected, actual);
    }
}
