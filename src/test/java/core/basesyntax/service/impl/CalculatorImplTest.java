package core.basesyntax.service.impl;

import static core.basesyntax.model.Product.APPLE;
import static core.basesyntax.model.Product.BANANA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Calculator;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorImplTest {
    private static Calculator calculator;

    @BeforeAll
    static void beforeAll() {
        calculator = new CalculatorImpl(new OperationStrategyImpl());
    }

    @DisplayName("Check calculator with valid FruitTransaction")
    @Test
    public void calculate_validTransaction_ok() {
        List<FruitTransaction> transactions =
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        BANANA.getName(), 20));
        calculator.calculate(transactions);
        Map<String, Integer> expected = Map.of(BANANA.getName(), 20);
        assertEquals(expected, Storage.STORAGE_MAP);
    }

    @DisplayName("Check calculator with 2 valid FruitTransaction")
    @Test
    public void calculate_validMultipleTransactions_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE.getName(), 20),
                new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE.getName(), 10)
        );
        calculator.calculate(transactions);
        assertEquals(30, Storage.STORAGE_MAP.get(APPLE.getName()).intValue());
    }

    @AfterAll
    static void afterAll() {
        Storage.STORAGE_MAP.clear();
    }
}
