package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import org.junit.Assert;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
    @Test
    public void generateReport_Ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();

        Storage.fruitMap.put("banana", 152);
        Storage.fruitMap.put("apple", 90);

        String generatedReport = new ReportGeneratorServiceImpl().generateReport();

        Assert.assertEquals(expectedReport, generatedReport);

        Storage.fruitMap.clear();
    }
}
