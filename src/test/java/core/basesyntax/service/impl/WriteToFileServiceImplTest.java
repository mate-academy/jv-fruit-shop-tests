package core.basesyntax.service.impl;

import core.basesyntax.service.ReportService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class WriteToFileServiceImplTest {
    private static final String WRONG_PATH = "src/main/java/core/basesyntax/resources/wrong.csv";
    private static final String OUTPUT_PATH = "src/main/resources/test-report.csv";
    private static final String EXPECTED_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,164" + System.lineSeparator()
            + "apple,90";
    private final WriteToFileServiceImpl write = new WriteToFileServiceImpl();
    private final ReportService reportService = new ReportServiceImpl();

    @Test
    void writeToFile_wrongPath_notOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> write.writeToFile(WRONG_PATH, reportService.generateReport()));
    }

    @Test
    void writeToFile_CorrectReport_Ok() throws IOException {
        write.writeToFile(OUTPUT_PATH, EXPECTED_REPORT);
        String actualResult = Files.readString(Path.of(OUTPUT_PATH));
        Assert.assertEquals(EXPECTED_REPORT, actualResult);
    }
}
