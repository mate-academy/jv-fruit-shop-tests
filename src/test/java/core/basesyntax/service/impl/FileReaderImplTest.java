package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String PATH_OF_INPUT_FILE = "src/test/resources/input.csv";
    private static final String NOT_VALID_PATH_OF_INPUT_FILE = "notValid/test/resources/input.csv";
    private static FileReader fileReader;

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFromFileIsOk() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        List<String> actual = fileReader.readFromFile(PATH_OF_INPUT_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFilePathNotOk() {
        fileReader.readFromFile(NOT_VALID_PATH_OF_INPUT_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFilePathNullNotOk() {
        fileReader.readFromFile(null);
    }
}
