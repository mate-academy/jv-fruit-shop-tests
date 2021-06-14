package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReportBuilderImplTest {
    private final ReportBuilder report = new ReportBuilderImpl();

    /*@Test
    public void buildReportWithNull() {
        try {
            report.buildReport(null);
        } catch (NullPointerException e) {
            return;
        }
        fail("Method have to throw NullPointerException.");
    }*/

    /*@Test
    public void buildReportWithEmptyList() {
        List<String> emptyList = new ArrayList<>();
        try {
            report.buildReport(emptyList);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        fail("Method have to throw NullPointerException.");
    }

    @Test
    public void buildReportWithListWithSizeOne() {
        List<String> wrongDataList = new ArrayList<>();
        wrongDataList.add("b,banana,10");
        try {
            report.buildReport(wrongDataList);
        } catch (RuntimeException e) {
            return;
        }
        fail("Method have to throw RunTimeException.");
    }*/

    @Test
    public void buildReportWithWrongData_NotOk() {
        List<String> wrongDataList = new ArrayList<>();
        wrongDataList.add("Heading");
        wrongDataList.add("wrongOperation,banana,10");
        try {
            report.buildReport(wrongDataList);
        } catch (NullPointerException e) {
            return;
        }
        fail("Method have to throw NullPointerException.");
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
