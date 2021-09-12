package core.basesyntax;

import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.ReaderService;
import core.basesyntax.dao.ReaderServiceImp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceTest {
    private static ReaderService readerService;
    private static List<String> testInputList;
    private static final String wrongFilePath = "<:main:>";
    private static final String inFilePath = "src/main/resources/test-input.csv";
    private static final String[] testInput = new String[] {"b,banana,20",
            "s,banana,100", "p,banana,100", "r,banana,100"};
    private static final String[] resultReport = new String[] {"fruit,quantity",
            "banana,120"};
    private static final String[] emptyResultReport = new String[] {"fruit,quantity"};

    @Before
    public void initialize() {
        readerService = new ReaderServiceImp();
        testInputList = Stream.of(testInput).collect(Collectors.toList());
    }

    @Test
    public void readFromFile_ActivityCodes_Ok() {
        List<String> actual = readerService.readFromFile(inFilePath);
        assertTrue(actual.containsAll(testInputList));
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_ActivityCodes_NotOk() {
        readerService.readFromFile(wrongFilePath);
    }
}
