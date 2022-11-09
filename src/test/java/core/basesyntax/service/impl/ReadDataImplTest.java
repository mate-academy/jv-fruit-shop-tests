package core.basesyntax.service.impl;

import core.basesyntax.service.ReadData;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ReadDataImplTest {
    private static final String INPUT_FILE = "src/test/resources/fruitInfo.csv";
    private ReadData readData = new ReadDataImpl();

    @Test
    public void readFromFile_correctPath_ok() {
        List<String> actual = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,18",
                "p,banana,5",
                "s,banana,500");
        List<String> expected = readData.read(INPUT);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_incorrectPath_NotOk() {
        String filePath = "incorrectPath";
        readData.read(filePath);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_Null_NotOk() {
        String filePath = null;
        readData.read(filePath);
    }
}
