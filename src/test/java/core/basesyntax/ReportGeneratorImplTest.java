package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.report.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGeneratorImpl rg;

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
        rg = new ReportGeneratorImpl();
    }

    @Test
    void generateReport_ok() {
        StringBuilder expected = new StringBuilder("fruit,quantity");
        expected.append(System.lineSeparator()).append("banana,20")
                .append(System.lineSeparator()).append("apple,10")
                .append(System.lineSeparator());
        Storage.getStorage().put("banana", 20);
        Storage.getStorage().put("apple", 10);
        String actual = rg.getReport();
        assertEquals(expected.toString(), actual);
    }
}
