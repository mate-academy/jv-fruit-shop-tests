package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.StorageImpl;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    @AfterEach
    void tearDown() {
        StorageImpl.fruitStorage.clear();
    }

    @Test
    void getReport_Ok() {
        StringBuilder builder = new StringBuilder();

        String header = "fruit,quantity";
        StorageImpl.fruitStorage.put("apple",30);
        StorageImpl.fruitStorage.put("orange", 15);
        builder.append(header)
                .append(System.lineSeparator())
                .append("orange,15")
                .append(System.lineSeparator())
                .append("apple,30")
                .append(System.lineSeparator());
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        assertEquals(builder.toString(), reportGenerator.getReport());
    }

    @Test
    void getReportWithEmptyStorage_Ok() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String header = "fruit,quantity";
        assertEquals(header + System.lineSeparator(),
                reportGenerator.getReport());
    }
}
