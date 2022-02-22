package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceCsvTest {
    private static final String FILE_NAME_OK = "src/test/resources/database_ok.csv";
    private static final String EMPTY_FILE_NAME = "src/test/resources/database_empty.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceCsv();
    }

    @Test
    public void readData_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");

        List<String> actual = fileReaderService.readFromFile(FILE_NAME_OK);

        assertEquals(8, actual.size());
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readData_fileNotExist() {
        fileReaderService.readFromFile("");
    }

    @Test
    public void readData_emptyFile() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReaderService.readFromFile(EMPTY_FILE_NAME);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readData_null_notOk() {
        fileReaderService.readFromFile(null);
    }
}
