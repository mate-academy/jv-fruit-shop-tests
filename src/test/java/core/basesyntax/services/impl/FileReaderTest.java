package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.services.FileReader;
import java.util.Arrays;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static final String VALID_PATH_TO_FILE = "src/resources/data.txt";
    private static FileReader fileReader;

    @BeforeClass
    public static void setup() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_readValidPathAndFile_OK() {
        List<String> actual = fileReader.readFromFile(VALID_PATH_TO_FILE);
        List<String> expected = Arrays.asList("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_readWrongPath_notOK() {
        String wrongPathToFile = "src/data.txt";
        fileReader.readFromFile(wrongPathToFile);
    }
}
