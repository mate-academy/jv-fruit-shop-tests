package core.basesyntax.service.implementation;

import core.basesyntax.service.Reader;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderImplTest {
    private Reader reader;

    @Before
    public void setUp() {
        reader = new ReaderImpl();
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
