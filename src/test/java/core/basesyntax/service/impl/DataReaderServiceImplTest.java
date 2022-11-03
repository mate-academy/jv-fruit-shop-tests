package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.service.DataReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataReaderServiceImplTest {
    private static final String INPUT_PATH = "src/test/resources/inputForTests";
    private static final String INVALID_INPUT_PATH = "src/test/resources/input";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile";
    private static DataReaderService dataReaderService = new DataReaderServiceImpl();
    private static List<String> expectedList = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
        expectedList.add("s,banana,100");
        expectedList.add("r,apple,10");
    }

    @Test
    public void readFile_validInputFilePath_ok() {
        List<String> actual = dataReaderService.readFile(INPUT_PATH);
        assertEquals(expectedList, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_invalidInputFilePath_notOk() {
        dataReaderService.readFile(INVALID_INPUT_PATH);
    }

    @Test
    public void readFile_emptyInputFile_notOk() {
        List<String> actual = dataReaderService.readFile(EMPTY_FILE_PATH);
        assertNotEquals(expectedList, actual);
    }
}
