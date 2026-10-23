package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static Storage storage;
    private static ReportGeneratorImpl reportGenerator;

    @BeforeAll
    public static void setUp() {
        storage = new Storage();
        reportGenerator = new ReportGeneratorImpl(storage);
    }

    @Test
    public void reportGeneratorTest() {
        storage.add("apple", 10);
        String report = reportGenerator.getReport();
        assertEquals("fruit,quantity" + "\n" + "apple,10" + "\n", report);
    }

    @Test
    public void emptyStorageReportTest() {
        String report = reportGenerator.getReport();
        assertEquals("fruit,quantity" + "\n", report);
    }

    @AfterEach
    public void clearStorage() {
        storage.getAll().clear();
    }
}
