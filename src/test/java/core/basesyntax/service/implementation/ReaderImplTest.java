package core.basesyntax.service.implementation;

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
    public void readFromFile_validData_ok() {
        List<String> expected = List.of(
                "b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10", "p,apple,20",
                "p,banana,5", "s,banana,50");
        List<String> actual = reader.readFromFile("./src/main/resources/fruitInput.csv");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_invalidFilePath_notOk() {
        reader.readFromFile("fruitInput.csv");
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_emptyCsv_ok() {
        List<String> expected = List.of("");
        List<String> actual = reader.readFromFile("");
        Assert.assertEquals(expected, actual);
    }
}
