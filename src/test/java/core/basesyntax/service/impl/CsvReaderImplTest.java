package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CsvReaderImplTest {
    private Reader reader = new CsvReaderImpl();
    private String filePath = "src/test/resources/databaseTest.csv";

    @Test
    public void readOk() {
        List<String> actual = reader.readFromFile(filePath);
        List<String> expected = List.of("testText", "123");
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = AssertionError.class)
    public void read_notOk() {
        List<String> actual = reader.readFromFile(filePath);
        List<String> expected = List.of("fgh", "456");
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void readPathNotOk() {
        reader.readFromFile("badPath");
    }

    @Test(expected = NullPointerException.class)
    public void readPathIsNull_notOk() {
        reader.readFromFile(null);
    }
}
