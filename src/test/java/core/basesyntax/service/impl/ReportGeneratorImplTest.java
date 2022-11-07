package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGenerator;
import org.junit.After;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @Test
    public void generateReport_correctGenerateReport_ok() {
        Storage.storage.add(new Fruit("banana", 20));
        Storage.storage.add(new Fruit("apple", 100));
        StringBuilder builder = new StringBuilder();
        builder.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,20")
                .append(System.lineSeparator())
                .append("apple,100")
                .append(System.lineSeparator());
        String expected = builder.toString();
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    public void generateReport_emptyStorage_notOk() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
