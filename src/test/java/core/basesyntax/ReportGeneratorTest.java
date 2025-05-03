package core.basesyntax;

import core.basesyntax.converter.DataConverter;
import core.basesyntax.converter.DataConverterImpl;
import core.basesyntax.reportgenerator.ReportGenerator;
import core.basesyntax.reportgenerator.ReportGeneratorImpl;
import core.basesyntax.storage.Storage;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private ReportGenerator reportGenerator;
    private DataConverter converter;

    @BeforeEach
    void setup() {
        reportGenerator = new ReportGeneratorImpl();
        converter = new DataConverterImpl();
        Storage.fruits.clear();
    }

    @Test
    public void generateReport_ok() {
        Storage.addFruit("mango", 15);
        Storage.addFruit("kiwi", 30);
        Storage.addFruit("peach", 20);

        String actualReport = reportGenerator.getReport();
        String[] lines = actualReport.split(System.lineSeparator());

        List<String> expectedLines = List.of(
                "fruit,quantity",
                "mango,15",
                "kiwi,30",
                "peach,20"
        );

        for (String expectedLine : expectedLines) {
            Assert.assertTrue("Missing line: " + expectedLine,
                    List.of(lines).contains(expectedLine));
        }
    }

    @Test
    public void getReportEmpty_OK() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportGenerator.getReport();

        Assert.assertEquals(expectedReport,actualReport);
    }
}
