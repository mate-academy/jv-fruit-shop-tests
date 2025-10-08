package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    @Test
    void reportShouldContainHeaderAndLines() {
        Storage storage = new Storage();
        storage.put("banana", 2);
        storage.put("apple", 1);

        ReportGenerator rg = new ReportGeneratorImpl(storage);
        String report = rg.getReport();
        assertTrue(report.startsWith("fruit,quantity"));
        assertTrue(report.contains("banana,2"));
        assertTrue(report.contains("apple,1"));
    }
}
