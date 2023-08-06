package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.DataReader;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.ReportWriter;
import core.basesyntax.service.impl.exception.InvalidDataException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportWriterImplTest {
    private static final String REPORT_FILE_PATH = "src/test/resources/testOutput.csv";
    private static final String INVALID_REPORT_FILE_PATH = "invalid/invalid/invalid/invalid";
    private ReportWriter reportWriter;
    private ReportCreator reportCreator;
    private DataReader dataReader;

    @BeforeEach
    public void setUp() {
        dataReader = new DataReaderImpl();
        reportCreator = new ReportCreatorImpl();
        reportWriter = new ReportWriterImpl();
    }

    @Test
    void writeReportToFile_validParameters_Ok() {
        Storage.storage.put("banana", 70);
        Storage.storage.put("apple", 70);
        String report = reportCreator.createReport();
        reportWriter.writeReportToFile(report, REPORT_FILE_PATH);
        String result = dataReader.readDataFromFile(REPORT_FILE_PATH);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,70"
                + System.lineSeparator()
                + "apple,70";
        assertEquals(expected, result);
    }

    @Test
    void writeReportToFile_invalidFilePath_notOK() {
        String report = reportCreator.createReport();
        assertThrows(InvalidDataException.class,
                () -> reportWriter.writeReportToFile(report, INVALID_REPORT_FILE_PATH),
                "InvalidDataException expected to be thrown");
    }

    @AfterEach
    void storageClear() {
        Storage.storage.clear();
    }
}
