package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String INPUT_TEST_PATH = "src/test/resources/inputTest.csv";
    private static final String INPUT_TEST_TWO_PATH = "src/test/resources/inputTest2.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";
    private static final String NON_EXISTING_FILE_PATH = "src/test/resources/non_existingFile.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_readFromInputTest_Ok() {
        List<String> expected = List.of("type,fruit,amount",
                "b,apple,90",
                "b,banana,30",
                "s,banana,60",
                "s,banana,40",
                "p,banana,25",
                "r,apple,10",
                "p,apple,20",
                "p,banana,15");
        List<String> actual = fileReaderService.readFromFile(INPUT_TEST_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_readFromSecondInput_Ok() {
        List<String> expected = List.of("a,avocado,70", "q,kiwi,85","54 shirt");
        List<String> actual = fileReaderService.readFromFile(INPUT_TEST_TWO_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_readFromEmptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = fileReaderService.readFromFile(EMPTY_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void readFromFile_readFromNullPathFile_NotOk() {
        fileReaderService.readFromFile(null);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_readFromNonExistingPathFife_NotOk() {
        fileReaderService.readFromFile(NON_EXISTING_FILE_PATH);
    }
}
