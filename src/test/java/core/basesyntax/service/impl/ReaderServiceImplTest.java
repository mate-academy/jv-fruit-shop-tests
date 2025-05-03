package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static final String CORRECT_INPUT_FILE = "src/test/java/resources/InputInfo.csv";
    private static final String INCORRECT_INPUT_FILE = "src/main/resources/IncorrectName.csv";
    private static List<String> fruitInfo;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
        fruitInfo = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
    }

    @Test
    public void readInfoFromFile_correctInputFile_Ok() {
        List<String> actual = readerService.readInfoFromFile(CORRECT_INPUT_FILE);
        assertEquals(actual, fruitInfo);
    }

    @Test(expected = RuntimeException.class)
    public void readInfoFromFile_incorrectInputFile_notOk() {
        readerService.readInfoFromFile(INCORRECT_INPUT_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void readInfoFromFile_null_notOk() {
        readerService.readInfoFromFile(null);
    }
}
