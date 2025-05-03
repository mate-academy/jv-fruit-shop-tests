package service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import service.ReaderService;

public class ReaderServiceImplTest {
    private static final String FILE_FOR_READING = "src/test/resources/input-test.csv";
    private static final String WRONG_FILE_FOR_READING = "src/main/resources/input-wrong.csv";
    private static List<String> expected;

    private static ReaderService readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();

        expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
    }

    @Test
    public void read_FromRightFile_Ok() {
        List<String> actual = readerService.read(FILE_FOR_READING);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_FromWrongFile_Ok() {
        readerService.read(WRONG_FILE_FOR_READING);
        fail(RuntimeException.class.getName());
    }

    @Test(expected = NullPointerException.class)
    public void read_FromNull_NotOk() {
        readerService.read(null);
    }
}
