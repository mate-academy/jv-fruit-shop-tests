package core.basesyntax.service;

import static core.basesyntax.transaction.Operation.BALANCE;
import static core.basesyntax.transaction.Operation.PURCHASE;
import static core.basesyntax.transaction.Operation.RETURN;
import static core.basesyntax.transaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.QuantityCalculatorServiceImpl;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import core.basesyntax.transaction.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuantityCalculatorServiceImplTest {
    private QuantityCalculatorService quantityCalculatorService;
    private List<FruitTransaction> fruitTransactions;

    @BeforeEach
    void setUp() {
        OperationHandlerStrategy operationHandlerStrategy = new OperationHandlerStrategyImpl(Map.of(
                BALANCE, new BalanceOperationHandler(),
                RETURN, new ReturnOperationHandler(),
                PURCHASE, new PurchaseOperationHandler(),
                SUPPLY, new SupplyOperationHandler()
        ));
        quantityCalculatorService = new QuantityCalculatorServiceImpl(operationHandlerStrategy);
        fruitTransactions = new ArrayList<>();
    }

    @Test
    void calculate_EmptyTransactionList_notOk() {
        assertDoesNotThrow(() -> quantityCalculatorService.calculate(fruitTransactions));
    }

    @Test
    void addData_validData_ok() {
        fruitTransactions = List.of(
                new FruitTransaction(BALANCE, "banana", 100),
                new FruitTransaction(PURCHASE, "banana", 90),
                new FruitTransaction(SUPPLY, "banana", 100),
                new FruitTransaction(RETURN, "banana", 50)
        );
        fruitTransactions.forEach(transaction ->
                quantityCalculatorService.calculate(List.of(transaction)));
        System.out.println("Storage: " + Storage.FRUITS);
        int expected = 160;
        int actual = Storage.FRUITS.get("banana");
        Assertions.assertEquals(expected, actual);
    }
}
