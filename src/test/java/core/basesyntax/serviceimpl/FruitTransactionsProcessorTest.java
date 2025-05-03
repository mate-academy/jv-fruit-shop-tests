package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.handlers.BalanceOperationHandler;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.PurchaseOperationHandler;
import core.basesyntax.handlers.ReturnOperationHandler;
import core.basesyntax.handlers.SupplierOperationHandler;
import core.basesyntax.service.TransactionsProcessor;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationsStrategy;
import core.basesyntax.transaction.FruitTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionsProcessorTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int FRUIT_BALANCE_NUM = 100;
    private static final int BANANA_RETURN_NUM = 20;
    private static final int APPLE_PURCHASE_NUM = 50;
    private static final int APPLE_EXPECTED_QUANTITY = 50;
    private static final int BANANA_EXPECTED_QUANTITY = 120;
    private static TransactionsProcessor fruitTransactionsProcessor;
    private static List<FruitTransaction> fruitTransactionList;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplierOperationHandler());
        handlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());

        OperationsStrategy operationsStrategy = new OperationsStrategy(handlerMap);
        fruitTransactionsProcessor = new FruitTransactionsProcessor(operationsStrategy);

        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setFruit(BANANA);
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setQuantity(FRUIT_BALANCE_NUM);

        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setFruit(APPLE);
        fruitTransaction2.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction2.setQuantity(FRUIT_BALANCE_NUM);

        FruitTransaction fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setFruit(BANANA);
        fruitTransaction3.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction3.setQuantity(BANANA_RETURN_NUM);

        FruitTransaction fruitTransaction4 = new FruitTransaction();
        fruitTransaction4.setFruit(APPLE);
        fruitTransaction4.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction4.setQuantity(APPLE_PURCHASE_NUM);

        fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(fruitTransaction1);
        fruitTransactionList.add(fruitTransaction2);
        fruitTransactionList.add(fruitTransaction3);
        fruitTransactionList.add(fruitTransaction4);
    }

    @Test
    void process_Valid_Transactions_Ok() {
        fruitTransactionsProcessor.processTransactions(fruitTransactionList);
        assertEquals(Storage.getFruitBalance().get(APPLE), APPLE_EXPECTED_QUANTITY);
        assertEquals(Storage.getFruitBalance().get(BANANA), BANANA_EXPECTED_QUANTITY);
    }
}
