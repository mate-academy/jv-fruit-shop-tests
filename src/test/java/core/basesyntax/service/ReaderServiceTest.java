package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.Utils;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String PATH = Utils.pathFix("src/test/resources/fruits.csv");
    private static final String EMPTY_PATH = "";
    private static final String TITLE = "type,fruit,quantity";
    private static final String FIRST_LINE = "b,banana,20";
    private static final String SECOND_LINE = "b,apple,100";
    private static final String THIRD_LINE = "s,banana,100";
    private static final String FOUR_LINE = "p,banana,13";
    private static final String FIVE_LINE = "r,apple,10";
    private static final String SIX_LINE = "p,apple,20";
    private static final String SEVEN_LINE = "p,banana,5";
    private static final String EIGHTH_LINE = "s,banana,50";
    private static ReaderService readerService;
    private static List<String> expected;
    private static File file;

    @BeforeClass
    public static void beforeAll() throws Exception {
        readerService = new ReaderServiceImpl();
        expected = new ArrayList<>();
        expected.add(TITLE);
        expected.add(FIRST_LINE);
        expected.add(SECOND_LINE);
        expected.add(THIRD_LINE);
        expected.add(FOUR_LINE);
        expected.add(FIVE_LINE);
        expected.add(SIX_LINE);
        expected.add(SEVEN_LINE);
        expected.add(EIGHTH_LINE);
    }

    @Test
    public void readerService_readFromFile_Ok() {
        file = new File(PATH);
        readerService = new ReaderServiceImpl();
        List<String> actual = readerService.readFromFile(PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readerService_readWithEmptyPath_NotOk() {
        readerService.readFromFile(EMPTY_PATH);
    }
}
