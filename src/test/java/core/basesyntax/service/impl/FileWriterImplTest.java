package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FileWriter;
import java.io.File;
import java.util.StringJoiner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TITLE = "fruit,quantity";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String APPLE_WITH_QUANTITY = "apple,20";
    private static final String BANANA_WITH_QUANTITY = "banana,50";
    private static final String NO_VALID_PATH = "/src/resource/img/report.csv";
    private static final File FINAL_REPORT
            = new File("src/main/resources/files/reportToWrite.csv");
    private static FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void writeData_toReport_Ok() {
        String expectedReport = new StringJoiner(System.lineSeparator(),"",System.lineSeparator())
                .add(TITLE).add(BANANA_WITH_QUANTITY).add(APPLE_WITH_QUANTITY).toString();
        fileWriter.write(expectedReport, FINAL_REPORT);
        Storage.addFruit(APPLE, 20);
        Storage.addFruit(BANANA, 50);
        String actualReport = new ReportGeneratorImpl().getReport();
        assertLinesMatch(expectedReport.lines(), actualReport.lines());
        Storage.clear();
    }

    @Test
    void writeData_toNonWritableLocation_notOk() {
        String report
                = TITLE + APPLE_WITH_QUANTITY + BANANA_WITH_QUANTITY;
        File nonWritableFile = new File(NO_VALID_PATH);
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(report, nonWritableFile));
    }
}
