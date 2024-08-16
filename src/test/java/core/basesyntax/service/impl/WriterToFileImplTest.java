package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderFromFile;
import core.basesyntax.service.WriterToFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterToFileImplTest {
    private static final String REPORT_FILE_NAME_OK = "src\\test\\resources\\Writer Test1.csv";
    private static final String INVALID_REPORT_FILE_NAME =
            "\\invalid_path\\Writer test invalid.csv";
    private static final String REPORT_CONTENT = "This is a test report.";
    private static final int INDEX_OF_FILE_CONTENT = 0;
    private ReaderFromFile reader;
    private WriterToFile writer;

    @BeforeEach
    void setUp() {
        reader = new ReaderFromFileImpl();
        writer = new WriterToFileImpl();
    }

    @Test
    void writeReportToFile_Ok() {
        writer.writeReportToFile(REPORT_CONTENT, REPORT_FILE_NAME_OK);
        String actual = reader.readFile(REPORT_FILE_NAME_OK).get(INDEX_OF_FILE_CONTENT);
        assertEquals(REPORT_CONTENT, actual);
    }

    @Test
    void writeToInvalidFile_NotOk() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            writer.writeReportToFile(REPORT_CONTENT, INVALID_REPORT_FILE_NAME);
        });

        assertEquals("Can't write data to file", exception.getMessage());
    }
}
