package fruitshop.service.impl;

import static org.junit.Assert.assertEquals;

import fruitshop.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderServiceImplTest {
    private static final String CORRECT_PATH = "src/test/resources/input.txt";
    private static final String INCORRECT_PATH = "test/resources/input.txt";
    private static final List<String> expected = new ArrayList<>();
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    private ReaderService readerService = new ReaderServiceImpl();

    @BeforeClass
    public static void beforeClass() {
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
    public void readFromFile_correctPath_ok() {
        List<String> actual = readerService.readFromFile(CORRECT_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_incorrectPath_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can't read data from file");
        readerService.readFromFile(INCORRECT_PATH);
    }

    @Test
    public void readFromFile_nullPath_notOk() {
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("Path is null");
        readerService.readFromFile(null);
    }
}
