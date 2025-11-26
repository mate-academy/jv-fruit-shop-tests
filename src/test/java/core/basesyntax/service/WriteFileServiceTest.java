package core.basesyntax.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriteFileServiceTest {
    private static final String INCORRECT_FILE_PATH = "";
    private static final String CORRECT_FILE_PATH = "src/test/resources/fruits_report.csv";
    private static final String EMPTY_REPORT = "";
    private WriteFileService writeFileService;

    @BeforeEach
    public void setUp() throws Exception {
        writeFileService = new WriteFileServiceImpl();
    }

    @Test
    public void writeFile_incorrectPath_notOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();

        Assertions.assertThrows(RuntimeException.class,
                () -> writeFileService.writeReportToFile(report, INCORRECT_FILE_PATH),
                "Incorrect path to write file");
    }

    @Test
    public void writeFile_emptyReport_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> writeFileService.writeReportToFile(EMPTY_REPORT, CORRECT_FILE_PATH),
                "Report is empty");
    }

    @Test
    public void writeFile_reportNull_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> writeFileService.writeReportToFile(null,CORRECT_FILE_PATH),
                "Report is null");
    }
}
