package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReportBuilderImplTest {
    private final ReportBuilder report = new ReportBuilderImpl();

    @Test(expected = NullPointerException.class)
    public void buildReportWithWrongData_NotOk() {
        List<String> wrongDataList = new ArrayList<>();
        wrongDataList.add("Heading");
        wrongDataList.add("wrongOperation,banana,10");
        report.buildReport(wrongDataList);
    }

    @Test
    public void buildReportWithCorrectData_Ok() {
        List<String> correctDataList = new ArrayList<>();
        correctDataList.add("Heading");
        correctDataList.add("b,banana,10");
        correctDataList.add("b,someUnknownFruit,1");
        String expectedReport = "fruit,quantity\nbanana,10"
                + "\nsomeUnknownFruit,1\n";
        String actualReport = report.buildReport(correctDataList);
        assertEquals(expectedReport,actualReport);
    }
}
