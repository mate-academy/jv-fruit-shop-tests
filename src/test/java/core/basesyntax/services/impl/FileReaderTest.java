package core.basesyntax.services.impl;

import core.basesyntax.services.FileReader;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileReaderTest extends Assert {
    private static final String VALID_PATH_TO_FILE = "src/resources/data.txt";

    @Test
    public void readFromFile_readValidPathAndFile_OK() {
        FileReader fileReader = new FileReaderImpl();
        List<String> actual = fileReader.readFromFile(VALID_PATH_TO_FILE);
        List<String> expected = Arrays.asList("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_readWrongPath_notOK() {
        String wrongPathToFile = "src/data.txt";
        FileReader fileReader = new FileReaderImpl();
        fileReader.readFromFile(wrongPathToFile);
    }
}
