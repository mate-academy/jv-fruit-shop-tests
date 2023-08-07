package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private FileWriterService fileWriterService;
    private FileReaderService fileReaderService;
    private String report;
    private String reportFile;

    @BeforeEach
    void setUp() {
        fileWriterService = new FileWriterServiceImpl();
        fileReaderService = new FileReaderServiceImpl();
        report = "fruit,quantity" + System.lineSeparator()
                + "apricot,15" + System.lineSeparator();
        reportFile = "src/test/resources/testReport.csv";
    }

    @Test
    void write_validData_ok() {
        fileWriterService.write(report, reportFile);
        String actualReport = fileReaderService.read(reportFile);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apricot,15" + System.lineSeparator();
        assertEquals(actualReport, expectedReport);
    }

    @Test
    void write_nullReportFileData_notOk() {
        reportFile = "";
        assertThrows(RuntimeException.class, () -> fileWriterService.write(report, reportFile));
    }

    @AfterEach
    public void afterEachTest() {
        Storage.STORAGE.clear();
    }
}
