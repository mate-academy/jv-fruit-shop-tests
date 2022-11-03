package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderServiceImplTest {
    private static final String CANNOT_FIND_FILE_BY_PATH = "Can't find file by path: ";
    private static final String INVALID_PATH = "";
    private static final String FRUITS_CSV_FILEPATH = "src/main/resources/fruits.csv";
    private static ReaderService readerService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_filePathInvalid_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage(CANNOT_FIND_FILE_BY_PATH);
        readerService.readFromFile(INVALID_PATH);
    }

    @Test
    public void readFromFile_filePathValid_ok() {
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
        List<String> actual = readerService.readFromFile(FRUITS_CSV_FILEPATH);
        assertEquals(actual, expected);
    }
}
