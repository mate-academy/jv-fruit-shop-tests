package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.FruitTransactionHandlerImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceImpl;
import core.basesyntax.strategy.impl.PurchaseImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionHandlerImplTest {
    private static FruitTransactionHandler fruitTransactionHandler;
    private static List<FruitTransaction> fruitTransactions;
    private static OperationStrategy operationStrategy;
    private static FruitTransaction transactionBalance;
    private static FruitTransaction transactionPurchase;

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategy(createOperationHandlerMap());
        fruitTransactionHandler = new FruitTransactionHandlerImpl(operationStrategy);
        fruitTransactions = new ArrayList<>();
        transactionBalance = new FruitTransaction(Operation.BALANCE, "apple", 10);
        transactionPurchase = new FruitTransaction(Operation.PURCHASE, "apple", 5);
    }

    @Test
    void testHandleFruitTransactions() {
        fruitTransactions.add(transactionBalance);
        fruitTransactions.add(transactionPurchase);
        fruitTransactionHandler.handleFruitTransactions(fruitTransactions);
        Assertions.assertTrue(operationStrategy
                .getHandler(Operation.BALANCE) instanceof BalanceImpl);
        Assertions.assertTrue(operationStrategy
                .getHandler(Operation.PURCHASE) instanceof PurchaseImpl);
    }

    private Map<Operation, OperationHandler> createOperationHandlerMap() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceImpl());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseImpl());
        return operationHandlerMap;
    }
}
