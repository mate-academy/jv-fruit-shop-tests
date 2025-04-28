package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String HEADER = "fruit,quantity";
    private final Storage storage = new StorageImpl();
    private final StringBuilder builder = new StringBuilder();
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @Test
    void getReport_Ok() {
        storage.setFruitBalance("apple",30);
        storage.setFruitBalance("orange", 15);
        builder.append(HEADER)
                .append(System.lineSeparator())
                .append("orange,15")
                .append(System.lineSeparator())
                .append("apple,30")
                .append(System.lineSeparator());
        assertEquals(builder.toString(), reportGenerator.getReport());
    }
}
