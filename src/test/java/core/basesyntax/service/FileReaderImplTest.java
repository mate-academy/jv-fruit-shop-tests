package core.basesyntax.service;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String VALID_FILE_PATH
            = "src/test/java/core/basesyntax/resources/input.csv";
    private static final String WRONG_FILE_PATH = "src/test/wrongPath/input.csv";
    private static FileReader fileReader;

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_wrongPath_notOkk() {
        fileReader.readFromFile(WRONG_FILE_PATH);
    }

    @Test
    public void readFromFile_fileExist_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = fileReader.readFromFile(VALID_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }
}

