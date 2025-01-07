package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator = new ReportGeneratorImpl();
    private String report;
    private Map<String, Integer> fruitTransactionList = new HashMap<>();

    @BeforeEach
    void before() {
        fruitTransactionList.put("banana", 0);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity" + System.lineSeparator());
        stringBuilder.append("banana,0");
        report = stringBuilder.toString();
    }

    @Test
    void getReport_Ok() {
        assertEquals(report, reportGenerator.getReport(fruitTransactionList));
    }
}
