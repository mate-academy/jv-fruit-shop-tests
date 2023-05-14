package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private final FruitDao dao = new FruitDaoImpl();
    private final OperationHandler handler = new ReturnOperationHandler();

    @Before
    public void setUp() {
        final List<FruitTransaction> transactionsList = new ArrayList<>();
        final FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("banana");
        transaction.setQuantity(100);
        transactionsList.add(transaction);
        final FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction1.setFruit("banana");
        transaction1.setQuantity(10);
        transactionsList.add(transaction1);
        final FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction2.setFruit("banana");
        transaction2.setQuantity(7);
        transactionsList.add(transaction2);
        final FruitTransaction transaction3 = new FruitTransaction();
        transaction3.setOperation(FruitTransaction.Operation.BALANCE);
        transaction3.setFruit("apple");
        transaction3.setQuantity(10);
        transactionsList.add(transaction3);
        final FruitTransaction transaction4 = new FruitTransaction();
        transaction4.setOperation(FruitTransaction.Operation.RETURN);
        transaction4.setFruit("apple");
        transaction4.setQuantity(2);
        transactionsList.add(transaction4);
        final FruitTransaction transaction5 = new FruitTransaction();
        transaction5.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction5.setFruit("apple");
        transaction5.setQuantity(100);
        transactionsList.add(transaction5);
        for (FruitTransaction tr : transactionsList) {
            dao.add(tr);
        }
    }

    @Test
    public void handle_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("apple");
        transaction.setQuantity(1);
        handler.handle(transaction);
        FruitTransaction expected = new FruitTransaction();
        expected.setQuantity(11);
        expected.setOperation(FruitTransaction.Operation.BALANCE);
        expected.setFruit("apple");
        List<FruitTransaction> actualList = dao.getByOperation(FruitTransaction.Operation.BALANCE);
        FruitTransaction actual = null;
        for (FruitTransaction tr : actualList) {
            if (tr.getFruit().equals("apple")) {
                actual = tr;
            }
        }
        Assert.assertEquals(expected.getQuantity(), actual.getQuantity());
    }
}
