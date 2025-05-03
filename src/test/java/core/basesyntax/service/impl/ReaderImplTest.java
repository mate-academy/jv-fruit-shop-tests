package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static Reader reader;

    @BeforeClass
    public static void setUp() {
        reader = new ReaderImpl();
    }

    @Test
    public void readCorrect_ok() {
        List<String> actual = reader.readFromFile("src/test/resources/testData.csv");
        List<String> expected = List.of("fruit,quantity", "b,apple,12", "s,apple,8");
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void read_NullFile_notOk() {
        reader.readFromFile(null);
    }

    @Test (expected = RuntimeException.class)
    public void readFile_WithIncorrectPath_notOk() {
        reader.readFromFile("src/test/wrongDirectory/testData.csv");
    }
}
