package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String INPUT_TEST_PATH = "src/test/java/core/basesyntax"
            + "/recources/inputFileTest1.csv";
    private static final String INCORRECT_TEST_PATH = "src/test/java/core/basesyntax"
            + "/recources/input.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readerService_readFromCorrectPath_ok() {
        List<String> expected = List.of(
                "type,fruit,quantity", "b,banana,20", "b,apple,100", "b,oranges,240",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,oranges,20",
                "p,apple,20", "p,banana,5", "s,banana,50", "r,oranges,50");
        List<String> actual = readerService.readFromFile(INPUT_TEST_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readerService_readFromIncorrectPath_notOk() {
        readerService.readFromFile(INCORRECT_TEST_PATH);
    }
}
