package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.StorageOfData;
import core.basesyntax.service.impl.ReportBuilderServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportBuilderServiceTest {
    private static ReportBuilderService reportBuilder;

    @BeforeClass
    public static void setUp() {
        reportBuilder = new ReportBuilderServiceImpl();
    }

    @Test
    public void create_emptyStorage_ok() {
        String expected = "fruit,quantity";
        String actual = reportBuilder.buildReport();
        assertEquals(expected, actual);
    }

    @Test
    public void create_trueBuild_ok() {
        StringBuilder builder = new StringBuilder("fruit,quantity");
        String expected = builder.append(System.lineSeparator())
                .append("key")
                .append(",")
                .append(10)
                .toString();
        StorageOfData.fruitsData.put("key", 10);
        String actual = reportBuilder.buildReport();
        assertEquals(expected, actual);

    }
}
