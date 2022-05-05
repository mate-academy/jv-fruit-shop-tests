package core.basesyntax.service.implementations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.inerfaces.ReportMaker;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerImplTest {
    private static ReportMaker reportMaker;

    @BeforeClass
    public static void beforeAll() {
        reportMaker = new ReportMakerImpl();
    }

    @Test
    public void formReport_validReport_Ok() {
        Storage.storage.put(new Fruit("apple"), 15);
        Storage.storage.put(new Fruit("pear"), 25);
        StringBuilder builder = new StringBuilder();
        String expected = builder.append("apple,15")
                .append(System.lineSeparator())
                .append("pear,25").append(System.lineSeparator()).toString();
        String actual = reportMaker.formReport();
        Assert.assertEquals(expected, actual);
    }
}
