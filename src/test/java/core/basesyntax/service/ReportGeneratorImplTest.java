package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        Storage storage = new Storage(new HashMap<>());
        storage.addFruitWithQuantity("banana", 20);
        storage.addFruitWithQuantity("apple", 100);
        reportGenerator = new ReportGeneratorImpl(storage);
    }

    @Test
    public void getReport_ok() {
        StringBuilder expected = new StringBuilder();
        expected.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,20").append(System.lineSeparator())
                .append("apple,100").append(System.lineSeparator());
        String actual = reportGenerator.getReport();
        assertEquals(expected.toString(), actual);
    }
}
