package core.basesyntax.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterImplTest {
    private static final String validPathToFile
            = "src/test/java/core/basesyntax/filetest/writeTest.csv";
    private static final String invalidPathToFile
            = "src/test/java/basesyntax/filetest/writeTest.csv";
    private static final String InvalidPathMessage
            = "Can't write into file: ";
    private static final String SPLITTER = ",";
    private static final int HEAD_INDEX = 0;
    private static final int FRUIT_INDEX = 0;
    private static final int QUANTITY_INDEX = 1;
    private static CsvFileWriter csvFileWriter;
    private static CsvFileReader csvFileReader;
    private static Map<String, Integer> report;

    @BeforeClass
    public static void setUp() {
        csvFileWriter = new CsvFileWriterImpl();
        csvFileReader = new CsvFileReaderImpl();
        report = new HashMap<>();
        report.put("banana", 10);
        report.put("apple", 10);
    }

    @Test
    public void writeWorkability_Ok() {
        csvFileWriter.write(validPathToFile, report);
        List<String> reportList = csvFileReader.read(validPathToFile);
        reportList.remove(HEAD_INDEX);
        long equalityCheck = reportList.stream()
                .map(s -> s.split(SPLITTER))
                .filter(s -> report.get(s[FRUIT_INDEX]).equals(Integer.parseInt(s[QUANTITY_INDEX])))
                .count();
        Assert.assertEquals(reportList.size(), equalityCheck);

    }

    @Test
    public void writeInvalidPath_NotOk() {
        RuntimeException exception = Assert.assertThrows(RuntimeException.class,
                () -> csvFileWriter.write(invalidPathToFile, report));
        Assert.assertEquals(InvalidPathMessage + invalidPathToFile,
                exception.getMessage());
    }
}
