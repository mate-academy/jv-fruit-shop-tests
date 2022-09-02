package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static final String FILE_INPUT = "src/test/resources/test_input.csv";
    private static final String EMPTY = "src/test/resources/test_emptyFile.csv";

    @BeforeClass
    public static void setUp() throws Exception {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFile_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        List<String> actual = readerService.readFromFile(FILE_INPUT);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readString_NotOk() {
        String test = "banana";
        readerService.readFromFile(test);
    }

    @Test
    public void read_EmptyFile_NotOk() {
        List<String> expected = new ArrayList<>();
        List<String> actual = readerService.readFromFile(EMPTY);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void read_FileAsNull_NotOk() {
        readerService.readFromFile(null);
    }
}
