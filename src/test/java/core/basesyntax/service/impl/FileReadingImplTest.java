package core.basesyntax.service.impl;

import core.basesyntax.service.FileReading;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReadingImplTest {
    private static final String PATH_OF_INPUT_FILE = "src/test/resources/input.csv";
    private static FileReading reader;

    @BeforeClass
    public static void setUp() {
        reader = new FileReadingImpl();
    }

    @Test
    public void readFromFileOk() {
        List<String> stringList = new ArrayList<>();
        stringList.add("type,fruit,quantity");
        stringList.add("b,banana,20");
        stringList.add("b,apple,100");
        stringList.add("s,banana,100");
        stringList.add("p,banana,13");
        stringList.add("r,apple,10");
        List<String> actual = reader.readFromFile(PATH_OF_INPUT_FILE);
        Assert.assertEquals(actual, stringList);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFileNotOk() {
        reader.readFromFile("notValid/test/resources/input.csv");
    }

    @Test(expected = RuntimeException.class)
    public void readFromFilePathNull() {
        reader.readFromFile(null);
    }
}
