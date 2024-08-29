package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Report;
import core.basesyntax.model.enums.Operation;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.QuantityIncrementer;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.QuantityIncrementerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static ShopService service;

    @BeforeAll
    static void beforeAll() {
        QuantityIncrementer incrementer = new QuantityIncrementerImpl();
        Map<Operation, OperationHandler> handlersMap = Map.of(
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler(incrementer),
                Operation.PURCHASE, new PurchaseOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler(incrementer)
        );
        OperationStrategy strategy = new OperationStrategyImpl(handlersMap);
        service = new ShopServiceImpl(strategy);
    }

    @Test
    void process_validTransactions_shouldProcessCorrectly() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "apple", 50),
                new FruitTransaction(Operation.PURCHASE, "apple", 20),
                new FruitTransaction(Operation.RETURN, "apple", 5)
        );

        Report report = service.process(transactions);

        assertEquals(Map.of("apple", 135), report.getFruitQuantityMap(),
                "The report should reflect correct processing of transactions");
    }

    @Test
    void process_balanceNotFirst_shouldThrowException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 15),
                new FruitTransaction(Operation.SUPPLY, "apple", 50),
                new FruitTransaction(Operation.BALANCE, "apple", 100)
        );

        assertThrows(IllegalStateException.class, () -> service.process(transactions),
                "Expected exception to be thrown when balance is not the first operation");
    }
}
