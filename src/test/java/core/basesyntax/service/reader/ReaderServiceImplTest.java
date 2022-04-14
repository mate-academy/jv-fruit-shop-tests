package core.basesyntax.service.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {

    private static ReaderService readerService;
    private static String path;
    private static List<String> expected;
    private List<String> actual;

    @Before
    public void setUp() {
        path = "src/test/resources/inputFile.csv";
        readerService = new ReaderServiceImpl();
        expected = new ArrayList<>();
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
    public void readerServiceImpl_correct_List_Ok() {
        actual = readerService.read(path);
        assertEquals(actual, expected);
    }

    @Test
    public void readerServiceImpl_wrong_List_Ok() {
        expected.add("777");
        actual = readerService.read(path);
        assertNotEquals(actual, expected);
    }

}
