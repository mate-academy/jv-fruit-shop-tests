package core.basesyntax.service;

import core.basesyntax.service.impl.MyCsvReaderImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyReaderTest {
    private MyReader reader;

    @Before
    public void setUp() {
        reader = new MyCsvReaderImpl();
    }

    @Test
    public void readFromFile_validData_ok() {
        List<String> expected = List.of("b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual = reader.readFromFile("src/test/resources/inputTest.csv");
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_invalidFilePath_notOk() {
        reader.readFromFile(";;");
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_emptyCsv_ok() {
        List<String> expected = List.of("");
        List<String> actual = reader.readFromFile("src/test/resources/empty.csv");
        Assert.assertEquals(actual, expected);
    }
}
