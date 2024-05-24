package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.services.ReportWriter;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportWriterImplTest {
    private static final String TITLE = "fruit,quantity";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String APPLE_WITH_QUANTITY = "apple,20";
    private static final String BANANA_WITH_QUANTITY = "banana,50";
    private static final File FINAL_REPORT
            = new File("src/main/resources/reports/final-report.csv");
    private static final String NO_VALID_PATH = "/src/resource/img/report.csv";
    private static ReportWriter reportWriter;

    @BeforeEach
    void setUp() {
        reportWriter = new ReportWriterImpl();
    }

    @Test
    public void writeData_toReport_Ok() throws IOException {
        String expectedReport = TITLE + System.lineSeparator() + BANANA_WITH_QUANTITY
                + System.lineSeparator() + APPLE_WITH_QUANTITY;
        reportWriter.write(expectedReport, FINAL_REPORT);
        Storage.fruits.put(APPLE, 20);
        Storage.fruits.put(BANANA, 50);
        String actualReport = new ReportGeneratorImpl().getReport();
        assertLinesMatch(expectedReport.lines(), actualReport.lines());
        Storage.fruits.clear();
    }

    @Test
    void writeData_toNonWritableLocation_notOk() {
        String report = TITLE + APPLE_WITH_QUANTITY + BANANA_WITH_QUANTITY;
        File nonWritableFile = new File(NO_VALID_PATH);
        assertThrows(RuntimeException.class,
                () -> reportWriter.write(report, nonWritableFile));
    }
}
