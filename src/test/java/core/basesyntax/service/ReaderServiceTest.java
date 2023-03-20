package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.File;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String TRUE_PATH = "src/test/resources/fruits.csv";
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

    @BeforeClass
    public static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readerService_readFromFile_Ok() {
        List<String> expected = List.of(TITLE, FIRST_LINE, SECOND_LINE, THIRD_LINE,
                                        FOUR_LINE, FIVE_LINE, SIX_LINE, SEVEN_LINE, EIGHTH_LINE);
        List<String> actual = readerService.readFromFile(pathFix(TRUE_PATH));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readerService_readWithEmptyPath_NotOk() {
        readerService.readFromFile(EMPTY_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void readerService_readWithNullPath_NotOk() {
        readerService.readFromFile(null);
    }

    private String pathFix(String filePath) {
        filePath = filePath.replace("\\", File.separator);
        filePath = filePath.replace("/", File.separator);
        return filePath;
    }
}
