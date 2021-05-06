package core.basesyntax.model.dto.impl;

import core.basesyntax.storage.DataBase;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportHandlerImplTest {
    private static final String FIRST_LINE = "fruit,quantity";
    private static final String COLUMN_SEPARATOR = ",";
    private static Map<String, Integer> map;
    private static ReportHandlerImpl reportHandler = new ReportHandlerImpl();

    @BeforeClass
    public static void beforeClass() throws Exception {
        map = DataBase.getDataBase();
    }

    @Test
    public void makeReportTest_Ok() {
        String expected = reportHandler.makeReport();
        StringBuilder report = new StringBuilder();
        String actual = "";
        report.append(FIRST_LINE)
                .append(System.lineSeparator());
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            report.append(entry.getKey())
                    .append(COLUMN_SEPARATOR)
                    .append(entry.getValue().toString())
                    .append(System.lineSeparator());
            actual = report.toString();
        }
        Assert.assertEquals(expected, actual);
    }

}
