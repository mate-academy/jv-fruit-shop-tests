package core.basesyntax.services;

import core.basesyntax.db.Storage;
import org.junit.Assert;
import org.junit.Test;

public class TestGenerator {

    @Test
    public void testGenerator_Ok() {
        Storage.FruitTransactionStorage.clear();
        Storage.FruitTransactionStorage.put("banana", 30);
        Storage.FruitTransactionStorage.put("apple", 40);
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,30" + System.lineSeparator() + "apple,40" + System.lineSeparator();
        Assert.assertEquals(expected, reportGenerator.generate());
    }
}
