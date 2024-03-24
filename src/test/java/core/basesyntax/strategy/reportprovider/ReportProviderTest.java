package core.basesyntax.strategy.reportprovider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportProviderTest {
    private static final List<FruitTransaction> DATA =
            List.of(new FruitTransaction(Operation.BALANCE, "banana", 20),
                    new FruitTransaction(Operation.BALANCE, "apple", 100),
                    new FruitTransaction(Operation.SUPPLY, "banana", 100),
                    new FruitTransaction(Operation.PURCHASE, "banana", 13)
            );

    private static final Map<String, Integer> EXPECTED =
            Map.of("banana", 107, "apple", 100);

    private static final Map<String, Integer> WRONG =
            Map.of("banana", 107, "apple", 110);

    private Map<String, Integer> actual;

    private ReportProvider reportProvider;

    @BeforeEach
    void setUp() {
        reportProvider = new ReportProviderImpl();
        actual = reportProvider.processData(DATA);
    }

    @Test
    public void reportProvider_Ok() {
        assertEquals(EXPECTED.size(), actual.size());
        for (Map.Entry<String, Integer> entry : EXPECTED.entrySet()) {
            String fruit = entry.getKey();
            int expectedQuantity = entry.getValue();
            int actualQuantity = actual.getOrDefault(fruit, 0);
            assertEquals(expectedQuantity, actualQuantity,
                    "Quantity of " + fruit + " is not correct");
        }
    }

    @Test
    public void reportProvider_NotOk() {
        assertNotEquals(WRONG, actual);
    }
}
