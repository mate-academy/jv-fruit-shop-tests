package core.basesyntax.service.impl;

import core.basesyntax.service.FileReading;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReadingImplTest {
    private static final String PATH_OF_INPUT_FILE = "src/test/resources/input.csv";
    private static FileReading fileReader;

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReadingImpl();
    }

    @Test
    public void readFromFileOk() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        List<String> actual = fileReader.readFromFile(PATH_OF_INPUT_FILE);
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFilePathNotOk() {
        fileReader.readFromFile("notValid/test/resources/input.csv");
    }

    @Test(expected = RuntimeException.class)
    public void readFromFilePathNull() {
        fileReader.readFromFile(null);
    }
}
