package core.basesyntax.services.impl;

import core.basesyntax.services.ReportGenerator;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;

class ReportGeneratorImplTest {
    private Storage storage = new Storage();
    private ReportGenerator reportGenerator;
    private Map<String, Integer> storage1 = storage.getStorage();
    private static final String HEADER = "fruit,quantity";
    private static final String COMMA = ",";

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl(storage);
        storage1.put("apple", 100);
        storage1.put("orange", 110);
        storage1.put("plum", 200);
    }

    @Test
    void returnReport_validReport_ok() {
        StringBuilder str = new StringBuilder();
        str.append(HEADER).append(System.lineSeparator());
        for (Map.Entry<String, Integer> map : storage1.entrySet()) {
            str.append(map.getKey()).append(COMMA).append(map.getValue()).append(System.lineSeparator());
        }

        String expectReport = str.toString();
        String actualReport = reportGenerator.getReport();

        Assertions.assertEquals(expectReport, actualReport);
    }
}
