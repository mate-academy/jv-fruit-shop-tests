package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseHandlerImpl;
import core.basesyntax.strategy.impl.ReturnHandlerImpl;
import core.basesyntax.strategy.impl.SupplyHandlerImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private static TransactionProcessor transactionProcessor;
    private static OperationStrategy operationStrategy;
    private static FruitDao fruitDao;
    private static final int EXPECTED_BANANA_QUANTITY = 120;
    private static final int EXPECTED_APPLE_QUANTITY = 80;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        operationStrategy = new OperationStrategy(Map.of(
                Operation.BALANCE, new BalanceHandlerImpl(),
                Operation.SUPPLY, new SupplyHandlerImpl(),
                Operation.PURCHASE, new PurchaseHandlerImpl(),
                Operation.RETURN, new ReturnHandlerImpl()));
        transactionProcessor = new TransactionProcessorImpl(operationStrategy);
    }

    @Test
    void processValidTransactions_Ok() {
        List<FruitTransaction> inputData = Arrays.asList(
                createFruitTransaction(Operation.BALANCE, "banana", 20),
                createFruitTransaction(Operation.BALANCE, "apple", 100),
                createFruitTransaction(Operation.SUPPLY, "banana", 100),
                createFruitTransaction(Operation.PURCHASE, "apple", 20));
        transactionProcessor.process(inputData);
        int actualBananaQuantity = fruitDao.get("banana");
        int actualAppleQuantity = fruitDao.get("apple");
        assertEquals(EXPECTED_BANANA_QUANTITY, actualBananaQuantity);
        assertEquals(EXPECTED_APPLE_QUANTITY, actualAppleQuantity);
    }

    @Test
    void processNullTransactions_NotOk() {
        assertThrows(NullPointerException.class, () ->
                transactionProcessor.process(null));
    }

    private FruitTransaction createFruitTransaction(
            Operation operation, String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(operation);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}
