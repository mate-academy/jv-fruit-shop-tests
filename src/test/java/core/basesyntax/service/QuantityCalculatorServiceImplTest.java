package core.basesyntax.service;

import static core.basesyntax.transaction.Operation.BALANCE;
import static core.basesyntax.transaction.Operation.PURCHASE;
import static core.basesyntax.transaction.Operation.RETURN;
import static core.basesyntax.transaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.service.impl.QuantityCalculatorServiceImpl;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import core.basesyntax.transaction.FruitTransaction;
import core.basesyntax.transaction.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuantityCalculatorServiceImplTest {
    private QuantityCalculatorService quantityCalculatorService;
    private List<FruitTransaction> fruitTransactions;

    @BeforeEach
    void setUp() {
        OperationHandlerStrategy operationHadlerStrategy = new OperationHandlerStrategyImpl(Map.of(
                BALANCE, new BalanceOperationHandler(),
                RETURN, new ReturnOperationHandler(),
                PURCHASE, new PurchaseOperationHandler(),
                SUPPLY, new SupplyOperationHandler()
        ));
        quantityCalculatorService = new QuantityCalculatorServiceImpl(operationHadlerStrategy);
        fruitTransactions = new ArrayList<>();
    }

    @Test
    void calculate_EmptyTransactionList_notOk() {
        assertDoesNotThrow(() -> quantityCalculatorService.calculate(fruitTransactions));
    }

    @Test
    void calculate_SingleTransaction_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(BALANCE, "apple", 5);
        fruitTransactions.add(fruitTransaction);
        assertDoesNotThrow(() -> quantityCalculatorService.calculate(fruitTransactions));
    }

    @Test
    void calculate_MultipleTransactions_ok() {
        FruitTransaction transaction1 = new FruitTransaction(BALANCE, "apple", 5);
        FruitTransaction transaction2 = new FruitTransaction(Operation.SUPPLY, "banana", 10);
        fruitTransactions.add(transaction1);
        fruitTransactions.add(transaction2);
        assertDoesNotThrow(() -> quantityCalculatorService.calculate(fruitTransactions));
    }
}
