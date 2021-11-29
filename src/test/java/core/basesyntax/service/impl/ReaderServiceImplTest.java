package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static String filePath;
    private static ReaderService reader;

    @BeforeClass
    public static void beforeClass() {
        filePath = "src/test/java/resources/testForReader.csv";
        reader = new ReaderServiceImpl();
    }

    @Test
    public void read_validFilePath_ok() {
        List<String> actual = reader.read(filePath);
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_invalidPathInnerWhiteSpace_notOk() {
        reader.read("src/test/java/ resources/testForReader.csv");
    }

    @Test(expected = RuntimeException.class)
    public void read_invalidPathEmptyLine_notOk() {
        reader.read("      ");
    }

    @Test(expected = RuntimeException.class)
    public void read_invalidPathNull_notOk() {
        reader.read(null);
    }
}
