package core.basesyntax;

import core.basesyntax.service.report.ReportMaker;
import core.basesyntax.service.report.ReportMakerImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class ReportMakerTest {
    private static final String EXPECTED_DATA1 = "fruit,quantity"
            + System.lineSeparator()
            + "banana,20";
    private static final ReportMaker reportMaker = new ReportMakerImpl();

    @AfterClass
    public static void afterClass() throws Exception {
        Files.deleteIfExists(Path.of("result.csv"));
    }

    @Test
    public void makeReport_notEmptyData_ok() {
        reportMaker.makeReport("banana,20");
        String actualData;
        try {
            actualData = Files.readString(Path.of("result.csv"));
            Assert.assertEquals(EXPECTED_DATA1, actualData);
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from report", e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void makeReport_EmptyData_notOk() {
        reportMaker.makeReport(null);
    }
}
