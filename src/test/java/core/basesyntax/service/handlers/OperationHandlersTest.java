package core.basesyntax.service.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Warehouse;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlersTest {

    private static List<Transaction> transactionList = new ArrayList<>();

    @BeforeClass
    public static void setUp() {
        transactionList.add(new Transaction(Transaction.Operation.getOperation("b"),
                new Fruit("banana"), Integer.valueOf("100")));
        transactionList.add(new Transaction(Transaction.Operation.getOperation("p"),
                new Fruit("banana"), Integer.valueOf("50")));
        transactionList.add(new Transaction(Transaction.Operation.getOperation("r"),
                new Fruit("banana"), Integer.valueOf("20")));
        transactionList.add(new Transaction(Transaction.Operation.getOperation("s"),
                new Fruit("banana"), Integer.valueOf("10")));
        transactionList.add(new Transaction(Transaction.Operation.getOperation("b"),
                new Fruit("apple"), Integer.valueOf("50")));
        transactionList.add(new Transaction(Transaction.Operation.getOperation("p"),
                new Fruit("apple"), Integer.valueOf("25")));
        transactionList.add(new Transaction(Transaction.Operation.getOperation("r"),
                new Fruit("apple"), Integer.valueOf("10")));
        transactionList.add(new Transaction(Transaction.Operation.getOperation("s"),
                new Fruit("apple"), Integer.valueOf("5")));
    }

    @After
    public void tearDown() {
        Warehouse.getWarehouse().clear();
    }

    @Test
    public void handle_normalTransactionList_Ok() {
        OperationStrategy operationStrategy = new OperationStrategyImpl();
        transactionList.forEach(transaction -> operationStrategy.get(transaction.getOperation())
                .handle(transaction.getFruit(),
                        transaction.getQuantity()
        ));
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 80);
        expected.put(new Fruit("apple"), 40);
        Map<Fruit, Integer> actual = Warehouse.getWarehouse();
        assertEquals(expected, actual);
    }
}
