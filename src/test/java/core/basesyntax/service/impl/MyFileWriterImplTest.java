package core.basesyntax.service.impl;

import static core.basesyntax.TestConstants.APPLE;
import static core.basesyntax.TestConstants.BANANA;
import static core.basesyntax.TestConstants.DEFAULT_QUANTITY;
import static core.basesyntax.TestConstants.REPORT_FILE_PATH_TO_WRITE;
import static core.basesyntax.TestConstants.REPORT_HEADER;
import static core.basesyntax.TestConstants.WRONG_PATH;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.MyFileWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MyFileWriterImplTest {
    private static MyFileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new MyFileWriterImpl();
    }

    @Test
    void writeData_toReport_Ok() {
        StringBuilder stringBuilder = new StringBuilder(REPORT_HEADER);
        Storage.getFruits().put(BANANA, DEFAULT_QUANTITY);
        Storage.getFruits().put(APPLE, DEFAULT_QUANTITY);
        String expected = stringBuilder.append(BANANA)
                .append(",")
                .append(DEFAULT_QUANTITY)
                .append(System.lineSeparator())
                .append(APPLE)
                .append(",")
                .append(DEFAULT_QUANTITY)
                .append(System.lineSeparator())
                .toString();
        fileWriter.write(expected, REPORT_FILE_PATH_TO_WRITE);
        String actualReport = new ReportGeneratorImpl().getReport();
        assertLinesMatch(expected.lines(), actualReport.lines());
        Storage.clear();
    }

    @Test
    void writeData_toNonWritableLocation_notOk() {
        String report
                = APPLE + BANANA;
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(report, WRONG_PATH));
    }
}
