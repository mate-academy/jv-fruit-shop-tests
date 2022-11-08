package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReadData;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class ReadDataImplTest {
    private static final String INPUT = "src/test/resources/fruitInfo.csv";
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

    @Test
    public void readFromFile_incorrectPath_NotOk() {
        String filePath = "incorrectPath";
        assertThrows(RuntimeException.class, () ->
                readData.read(filePath), "IncorrectPath" + Path.of(filePath));
    }

    @Test
    public void readFromFile_Null_NotOk() {
        String filePath = null;
        assertThrows(RuntimeException.class, () ->
                readData.read(filePath), "IncorrectPath" + filePath);
    }
}
