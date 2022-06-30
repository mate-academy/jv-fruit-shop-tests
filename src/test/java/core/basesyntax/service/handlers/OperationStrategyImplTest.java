package core.basesyntax.service.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import java.util.ArrayList;
import java.util.List;

import core.basesyntax.service.impl.CsvFileReaderImpl;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OperationStrategyImplTest {

    private static OperationStrategyImpl operationStrategyImpl = new OperationStrategyImpl();

    @BeforeClass
    public static void beforeClass() {
        operationStrategyImpl.getOperationHandlersMap().put(Transaction.Operation.BALANCE, new BalanceOperationHandler());
        operationStrategyImpl.getOperationHandlersMap().put(Transaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationStrategyImpl.getOperationHandlersMap().put(Transaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationStrategyImpl.getOperationHandlersMap().put(Transaction.Operation.RETURN, new ReturnOperationHandler());
    }

    @Test
    public void getBalanceOperation_Ok() {
        Class<BalanceOperationHandler> expected = BalanceOperationHandler.class;
        OperationHandler operationHandler = operationStrategyImpl.get(Transaction.Operation.BALANCE);
        Class<BalanceOperationHandler> actual = (Class<BalanceOperationHandler>) operationHandler.getClass();
    }

    @Test
    public void getPurchaseOperation_Ok() {
        Class<PurchaseOperationHandler> expected = PurchaseOperationHandler.class;
        OperationHandler operationHandler = operationStrategyImpl.get(Transaction.Operation.PURCHASE);
        Class<PurchaseOperationHandler> actual = (Class<PurchaseOperationHandler>) operationHandler.getClass();
    }

    @Test
    public void getReturnOperation_Ok() {
        Class<ReturnOperationHandler> expected = ReturnOperationHandler.class;
        OperationHandler operationHandler = operationStrategyImpl.get(Transaction.Operation.RETURN);
        Class<ReturnOperationHandler> actual = (Class<ReturnOperationHandler>) operationHandler.getClass();
    }

    @Test
    public void getSupplyOperation_Ok() {
        Class<SupplyOperationHandler> expected = SupplyOperationHandler.class;
        OperationHandler operationHandler = operationStrategyImpl.get(Transaction.Operation.SUPPLY);
        Class<SupplyOperationHandler> actual = (Class<SupplyOperationHandler>) operationHandler.getClass();
    }

    //    @BeforeClass
//    public static void setUp() {
//        transactionList.add(new Transaction(Transaction.Operation.getOperation("b"),
//                new Fruit("banana"), Integer.valueOf("100")));
//        transactionList.add(new Transaction(Transaction.Operation.getOperation("p"),
//                new Fruit("banana"), Integer.valueOf("50")));
//        transactionList.add(new Transaction(Transaction.Operation.getOperation("r"),
//                new Fruit("banana"), Integer.valueOf("20")));
//        transactionList.add(new Transaction(Transaction.Operation.getOperation("s"),
//                new Fruit("banana"), Integer.valueOf("10")));
//        transactionList.add(new Transaction(Transaction.Operation.getOperation("b"),
//                new Fruit("apple"), Integer.valueOf("50")));
//        transactionList.add(new Transaction(Transaction.Operation.getOperation("p"),
//                new Fruit("apple"), Integer.valueOf("25")));
//        transactionList.add(new Transaction(Transaction.Operation.getOperation("r"),
//                new Fruit("apple"), Integer.valueOf("10")));
//        transactionList.add(new Transaction(Transaction.Operation.getOperation("s"),
//                new Fruit("apple"), Integer.valueOf("5")));
//    }
//
//    @After
//    public void tearDown() {
//        Warehouse.getWarehouse().clear();
//    }
//
//    @Test
//    public void getOperation_Ok() {
//        OperationStrategy operationStrategy = new OperationStrategyImpl();
//        for (Transaction transaction : transactionList) {
//            operationStrategy.get(transaction.getOperation());
//        }
//
//    }
//
//    @Test
//    public void handle_Ok() {
//        OperationStrategy operationStrategy = new OperationStrategyImpl();
//        transactionList.forEach(transaction -> operationStrategy.get(transaction.getOperation())
//                .handle(transaction.getFruit(),
//                        transaction.getQuantity()
//        ));
//        Map<Fruit, Integer> expected = new HashMap<>();
//        expected.put(new Fruit("banana"), 80);
//        expected.put(new Fruit("apple"), 40);
//        Map<Fruit, Integer> actual = Warehouse.getWarehouse();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void handleEmptyTransactionList_Ok() {
//        List<Transaction> emptyTransactionList = new ArrayList<>();
//        for (Transaction transaction : transactionList) {
//            emptyTransactionList.add(transaction.clone());
//        }
//        emptyTransactionList.clear();
//        OperationStrategy operationStrategy = new OperationStrategyImpl();
//        emptyTransactionList.forEach(transaction -> operationStrategy.get(transaction.getOperation())
//                .handle(transaction.getFruit(),
//                        transaction.getQuantity()
//                ));
//        Map<Fruit, Integer> expected = new HashMap<>();
//        Map<Fruit, Integer> actual = Warehouse.getWarehouse();
//        assertEquals(expected, actual);
//    }
}
