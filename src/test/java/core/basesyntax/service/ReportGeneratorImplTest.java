package core.basesyntax.service;

import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @After
    public void cleareStorage() {
        Storage.FRUIT_MAP.clear();
    }

    @Test
    public void getReport_ok() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,152").append(System.lineSeparator())
                .append("apple,90");
        Storage.FRUIT_MAP.put("banana", 152);
        Storage.FRUIT_MAP.put("apple", 90);
        Assert.assertEquals(stringBuilder.toString(), reportGenerator.getReport());
    }
}
