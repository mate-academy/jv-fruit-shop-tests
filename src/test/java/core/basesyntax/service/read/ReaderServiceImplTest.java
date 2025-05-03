package core.basesyntax.service.read;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static final String VALID_DATA_PATH = "src/test/resources/validData.csv";
    private static final String INCORRECT_PATH = "src/test/validData.csv";

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void read_incorrectPath_NotOk() {
        readerService.read(INCORRECT_PATH);
    }

    @Test
    public void read_readInput_Ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100");
        List<String> actual = readerService.read(VALID_DATA_PATH);
        Assert.assertEquals(expected, actual);
    }
}
