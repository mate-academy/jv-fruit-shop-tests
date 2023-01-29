package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.serviceimpl.CsvFileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceTest {
    private static CsvFileReaderServiceImpl csvFileReaderService;
    private static final String FILE_INPUT_TEST_CSV = "src/test/resources/input_test.csv";
    private static final String FILE_INPUT_TEST_EMPTY_CSV = "src/test/resources/input_empty_test.csv";

    @BeforeClass
    public static void setUp() {
        csvFileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void readDataFromFileCsv_EmptyFilePath_NotOk() {
        List<String> actual = csvFileReaderService.readDataFromFileCsv("");
    }

    @Test
    public void readDataFromFileCsvTest_IsReadData_OK() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = csvFileReaderService.readDataFromFileCsv(FILE_INPUT_TEST_CSV);
        assertEquals(expected, actual);
    }

    @Test
    public void readDataFromFileCsvTest_EmptyFile_OK() {
        List<String> expected = new ArrayList<>();
        List<String> actual = csvFileReaderService.readDataFromFileCsv(
                FILE_INPUT_TEST_EMPTY_CSV);
        assertEquals(expected, actual);
    }
}
