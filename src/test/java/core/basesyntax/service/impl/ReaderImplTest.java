package core.basesyntax.service.impl;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ReaderImplTest {
    private static final ReaderImpl reader = new ReaderImpl();

    @Test
    public void readCorrect_ok() {
        List<String> actual = reader.readFromFile("src/test/resources/testData.csv");
        List<String> expected = List.of("fruit,quantity", "b,apple,12", "s,apple,8");
        Assert.assertEquals(actual, expected);
    }

    @Test (expected = RuntimeException.class)
    public void readEmptyFile_notOk() {
        reader.readFromFile(null);
    }

    @Test (expected = RuntimeException.class)
    public void readFileWithIncorrectPath_notOk() {
        reader.readFromFile("src/test/wrongDirectory/testData.csv");
    }
}
