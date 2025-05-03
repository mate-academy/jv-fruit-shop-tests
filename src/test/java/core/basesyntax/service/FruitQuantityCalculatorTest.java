package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitQuantityCalculatorImpl;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitQuantityCalculatorTest {
    private FruitQuantityCalculator fruitQuantityCalculator;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
            = Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandlerImpl(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandlerImpl(),
            FruitTransaction.Operation.RETURN, new ReturnOperationHandlerImpl(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationHandlerImpl());

    @BeforeEach
    void setUp() {
        fruitQuantityCalculator = new FruitQuantityCalculatorImpl(
                new OperationHandlerStrategyImpl(operationHandlerMap));
    }

    @Test
    void calculateQuantity_validData_Ok() {
        List<FruitTransaction> input = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 45),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 23)
        );
        fruitQuantityCalculator.calculateQuantity(input);
        Map<String, Integer> expected = new LinkedHashMap<>();
        expected.put("banana", 123);
        expected.put("apple", 45);
        Map<String, Integer> actual = Storage.dataStorage;
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.dataStorage.clear();
    }
}
