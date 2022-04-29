package core.basesyntax.service;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private Map<Operation, OperationHandler> operationHandlerMapTest = new HashMap<>();
    private OperationStrategy operationStrategy =
            new OperationStrategyImpl(operationHandlerMapTest);
    private FruitTransactionDao fruitTransactionDao = new FruitTransactionDaoImpl();

    @Before
    public void setUp() {
        operationHandlerMapTest.put(Operation.SUPPLY,
                new SupplyOperationHandler(fruitTransactionDao));
        operationHandlerMapTest.put(Operation.BALANCE,
                new BalanceOperationHandler(fruitTransactionDao));
        operationHandlerMapTest.put(Operation.PURCHASE,
                new PurchaseOperationHandler(fruitTransactionDao));
        operationHandlerMapTest.put(Operation.RETURN,
                new ReturnOperationHandler(fruitTransactionDao));
    }

    @Test
    public void getOperationHandlerNull_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(null);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(30);
        OperationHandler actualHandler = operationStrategy.getHandler(fruitTransaction);
        assertNull(actualHandler);
    }

    @Test
    public void getReturnOperationHandler_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.RETURN, "banana", 80);
        OperationHandler expected = new ReturnOperationHandler(fruitTransactionDao);
        OperationHandler actualHandler = operationStrategy.getHandler(fruitTransaction);
        System.out.println(expected.getClass());
        assertEquals(expected.getClass(), actualHandler.getClass());
    }

    @Test
    public void getPurchaseOperationHandler_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE, "banana", 80);
        OperationHandler expected = new PurchaseOperationHandler(fruitTransactionDao);
        OperationHandler actualHandler = operationStrategy.getHandler(fruitTransaction);
        System.out.println(expected.getClass());
        assertEquals(expected.getClass(), actualHandler.getClass());
    }

    @Test
    public void getBalanceOperationHandler_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, "banana", 55);
        OperationHandler expected = new BalanceOperationHandler(fruitTransactionDao);
        OperationHandler actualHandler = operationStrategy.getHandler(fruitTransaction);
        System.out.println(expected.getClass());
        assertEquals(expected.getClass(), actualHandler.getClass());
    }

    @Test
    public void getSupplyOperationHandler_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(Operation.SUPPLY);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(30);
        OperationHandler expected = new SupplyOperationHandler(fruitTransactionDao);
        OperationHandler actualHandler = operationStrategy.getHandler(fruitTransaction);
        System.out.println(expected.getClass());
        assertEquals(expected.getClass(), actualHandler.getClass());
    }
}
