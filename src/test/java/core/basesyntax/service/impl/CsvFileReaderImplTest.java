package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.CsvFileReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderImplTest {
    private static final String NOT_EXIST_FILE = "src/test/resources/notExist.csv";
    private static final String EMPTY_FILE = "src/test/java/resources/empty.csv";
    private static final String INPUT_FILE = "src/test/java/resources/input.csv";
    private static CsvFileReaderService csvFileReader;

    @BeforeClass
    public static void setUp() {
        csvFileReader = new CsvFileReaderImpl();
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_pathIsNull_notOk() {
        csvFileReader.readFromFile(null);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_notExist_notOk() {
        csvFileReader.readFromFile(NOT_EXIST_FILE);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_isEmpty_notOk() {
        csvFileReader.readFromFile(EMPTY_FILE);
    }

    @Test
    public void readFromFile_inputFile_ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        assertEquals(expected, csvFileReader.readFromFile(INPUT_FILE));
    }
}
