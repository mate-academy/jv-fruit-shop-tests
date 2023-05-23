package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.QuantityCalculatorServiceImpl;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class QuantityCalculatorServiceTest {
    private static QuantityCalculatorService calculatorService;

    @BeforeAll
    static void beforeAll() {
        calculatorService = new QuantityCalculatorServiceImpl(new OperationHandlerStrategyImpl());
    }

    @Test
    public void calculate_validTransaction_ok() {
        List<FruitTransaction> transactions =
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20));
        calculatorService.calculate(transactions);
        Map<String, Integer> expected = Map.of("apple", 20);
        Map<String, Integer> actual = Storage.dataStorage;
        assertEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        Storage.dataStorage.clear();
    }
}
