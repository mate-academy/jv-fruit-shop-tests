package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.CsvFileReaderService;
import core.basesyntax.service.CsvFileWriterService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static CsvFileWriterService csvFileWriterService;
    private static CsvFileReaderService csvFileReaderService;
    private static final String OUTPUT_FILE = "src/test/java/resources/output.csv";
    private static final String NOT_EXIST_FILE = "src/test/resources/notExist.csv";
    private static final String VALID_DATA = "validData";

    @BeforeClass
    public static void setUp() {
        csvFileWriterService = new CsvFileWriterServiceImpl();
        csvFileReaderService = new CsvFileReaderImpl();
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_pathNull_notOk() {
        csvFileWriterService.writeToFile(null, VALID_DATA);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_nullData_notOk() {
        csvFileWriterService.writeToFile(OUTPUT_FILE, null);
    }

    @Test
    public void writeToFile_validData_ok() {
        csvFileWriterService.writeToFile(OUTPUT_FILE, VALID_DATA);
        List<String> actual = csvFileReaderService.readFromFile(OUTPUT_FILE);
        List<String> expected = List.of(VALID_DATA);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile() {
        csvFileWriterService.writeToFile(NOT_EXIST_FILE, VALID_DATA);
    }
}
