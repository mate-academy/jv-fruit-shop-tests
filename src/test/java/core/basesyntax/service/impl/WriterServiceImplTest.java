package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriterService;
import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String REPORT = "fruit,quantity" + LINE_SEPARATOR + "apple,50"
            + LINE_SEPARATOR + "banana,60" + LINE_SEPARATOR + "orange,70";
    private static final String BALANCE_REPORT = "src/test/resources/Balance_Report.csv";
    private WriterService writerService = new WriterServiceImpl();
    private File file = new File("src/test/resources/Balance_Report.csv");

    @Test
    void writeWithIncorrectReportOrPathOrNoFileName_notOk() {
        String reportNull = null;
        String reportEmpty = "";
        String filePathEmpty = "";
        String filePathNull = null;
        String noFileName = "src/test/resources/";
        assertAll("Incorrect ReportString, Path to file or no File name",
                () -> assertThrows(RuntimeException.class, () -> writerService
                        .writeToFile(reportNull, BALANCE_REPORT)),
                () -> assertThrows(RuntimeException.class, () -> writerService
                        .writeToFile(reportEmpty, BALANCE_REPORT)),
                () -> assertThrows(RuntimeException.class, () -> writerService
                        .writeToFile(REPORT, filePathEmpty)),
                () -> assertThrows(RuntimeException.class, () -> writerService
                        .writeToFile(REPORT, filePathNull)),
                () -> assertThrows(RuntimeException.class, () -> writerService
                        .writeToFile(REPORT, noFileName))
        );
    }

    @Test
    void writeReportToFile_Ok() {
        writerService.writeToFile(REPORT, BALANCE_REPORT);
        assertTrue(file.exists());
    }

    @AfterEach
    void deleteFile() {
        file.delete();
    }
}
