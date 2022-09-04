package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String INPUT_FILE = "src/test/resources/input.csv";
    private static final String WRONG_FILE = "noFile.txt";
    private static ReaderServiceImpl readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void read_nullFile_notOk() {
        readerService.readFromFile(null);
    }

    @Test (expected = RuntimeException.class)
    public void read_noFile_notOk() {
        readerService.readFromFile(WRONG_FILE);
    }

    @Test
    public void read_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = readerService.readFromFile(INPUT_FILE);
        assertEquals(expected, actual);
    }
}
