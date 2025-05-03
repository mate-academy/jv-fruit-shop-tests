package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.implementation.CsvFileReaderServiceImpl;
import java.util.List;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/testInput.csv";
    private static final String INVALID_FILE_PATH = "src/test/resources/testInputFail.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/emptyTestFile.csv";
    private static final List<String> EXPECTED_RESULT = List.of(
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");

    private final CsvFileReaderService csvFileReaderService = new CsvFileReaderServiceImpl();

    @Test
    public void readFrom_readFile_Ok() {
        List<String> actual = csvFileReaderService.readFrom(INPUT_FILE_PATH);
        assertEquals(EXPECTED_RESULT, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFrom_fileNotFound_notOk() {
        csvFileReaderService.readFrom(INVALID_FILE_PATH);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for invalid path, but it wasn't");
    }

    @Test(expected = RuntimeException.class)
    public void readFrom_emptyFile_notOk() {
        csvFileReaderService.readFrom(PATH_TO_EMPTY_FILE);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for invalid path, but it wasn't");
    }
}
