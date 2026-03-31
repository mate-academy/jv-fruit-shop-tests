package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static Storage storage;
    private static ReportGeneratorImpl reportGenerator;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        reportGenerator = new ReportGeneratorImpl(storage);
    }

    @BeforeEach
    void clearStorage() {
        storage.clear();
    }

    @Test
    void getReportEmptyStorage() {
        String expected = "fruit,quantity\n";
        assertEquals(expected, reportGenerator.getReport());
    }

    @Test
    void convertToCsvEmptyStorageReturnOnlyHeader() {
        String expected = "fruit,quantity\n";

        assertEquals(expected, reportGenerator.convertToCsv(storage));
    }

    @Test
    void convertToCsvValidDate() {
        String expected = "fruit,quantity\napple,100\n";
        storage.set("apple", 100);

        assertEquals(expected, reportGenerator.convertToCsv(storage));
    }
}
