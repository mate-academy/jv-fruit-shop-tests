package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportBuilder;
import org.junit.jupiter.api.Test;

class ReportBuilderImplTest {
    private static final String EXPECTED_REPORT = """
            fruit,quantity
            banana,109
            apple,109""";
    private final ReportBuilder reportBuilder = new ReportBuilderImpl();

    @Test
    void reportBuilder_ValidData_Ok() {
        Storage.dataBase.put("banana", 109);
        Storage.dataBase.put("apple", 109);
        String actual = reportBuilder.build(Storage.dataBase);
        assertEquals(EXPECTED_REPORT, actual);
    }
}
