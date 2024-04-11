package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileServiceCsvImplTest {
    private static FileServiceCsvImpl fileServiceCsv;
    private static final String CORRECT_INPUT_FILE_NAME = "src/test/resources/input.csv";
    private static final String INVALID_FILE_NAME = "invalid/path/to/file.csv";
    private static final String CORRECT_REPORT_FILE_NAME = "src/test/resources/report.csv";
    private static final String NULL_FILE_NAME = null;

    @BeforeAll
    static void beforeAll() {
        fileServiceCsv = new FileServiceCsvImpl();
    }

    @Test
    void readFromFile_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = fileServiceCsv.read(CORRECT_INPUT_FILE_NAME);
        assertEquals(expected, actual);
    }

    @Test
    void readFromFile_NullFileName_notOk() {
        assertThrows(NullPointerException.class, () -> fileServiceCsv.read(NULL_FILE_NAME));
    }

    @Test
    void readFromFile_invalidFileName_notOk() {
        assertThrows(RuntimeException.class, () -> fileServiceCsv.read(INVALID_FILE_NAME));
    }

    @Test
    void writeToFile_ok() {
        List<String> dataToFile = new ArrayList<>();
        dataToFile.add("fruit,quantity" + System.lineSeparator());
        dataToFile.add("banana,152" + System.lineSeparator());
        dataToFile.add("apple,90" + System.lineSeparator());
        fileServiceCsv.write(CORRECT_REPORT_FILE_NAME, dataToFile);
        List<String> actual = fileServiceCsv.read(CORRECT_REPORT_FILE_NAME);
        List<String> expected = new ArrayList<>();
        expected.add("banana,152");
        expected.add("apple,90");
        assertEquals(expected, actual);
    }

    @Test
    void writeToFile_nullFileName_notOk() {
        List<String> report = new ArrayList<>();
        assertThrows(NullPointerException.class, () ->
                 fileServiceCsv.write(NULL_FILE_NAME, report));
    }

    @Test
    void writeToFile_InvalidFileName_notOk() {
        List<String> report = new ArrayList<>();
        assertThrows(RuntimeException.class, () ->
                 fileServiceCsv.write(INVALID_FILE_NAME, report));
    }
}
