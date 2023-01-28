package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.serviceimpl.CsvFileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CsvFileReaderServiceTest {
    private static CsvFileReaderServiceImpl csvFileReaderService;

    @Before
    public void setUp() {
        csvFileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void csvFileReader_FilePath_Empty() {
        List<String> actual = csvFileReaderService.readDataFromFileCsv("");
    }

    @Test
    public void csvFileReader_Read_OK() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = csvFileReaderService.readDataFromFileCsv(
                "src/test/resources/input_test.csv");
        assertEquals(expected, actual);
    }

    @Test
    public void csvFileReader_EmptyFile_OK() {
        List<String> expected = new ArrayList<>();
        List<String> actual = csvFileReaderService.readDataFromFileCsv(
                "src/test/resources/input_empty_test.csv");
        assertEquals(expected, actual);
    }
}
