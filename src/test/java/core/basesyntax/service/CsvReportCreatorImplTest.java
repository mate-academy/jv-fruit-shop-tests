package core.basesyntax.service;

import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CsvReportCreatorImplTest {

    private ReportCreator reportCreator;

    @Before
    public void beforEachTest() {
        reportCreator = new CsvReportCreatorImpl();
    }

    public String createReport(Map<String, Integer> data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry: data.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append(",")
                    .append(entry.getValue())
                    .append("\n");
        }
        return stringBuilder.toString();
    }

    @Test
    public void csvReportCreatorImpl_correctData_ok() {
        Storage.getStorage().put("ananas",150);
        String string = reportCreator.createReport(Storage.getStorage());
        Assert.assertEquals("ananas,150\n", string);
    }
}
