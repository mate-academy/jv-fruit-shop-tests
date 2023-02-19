package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private final OperationHandler handler = new PurchaseOperationHandler();
    private final FruitDao dao = new FruitDaoImpl();

    @Before
    public void setUp() throws Exception {
        List<FruitTransaction> transactionsList = new ArrayList<>();
        final FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("banana");
        transaction.setQuantity(100);
        transactionsList.add(transaction);
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
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("banana");
        transaction.setQuantity(9);
        handler.handle(transaction);
        FruitTransaction expected = new FruitTransaction();
        expected.setFruit("banana");
        expected.setOperation(FruitTransaction.Operation.BALANCE);
        expected.setQuantity(91);
        List<FruitTransaction> actualList = dao.getByOperation(FruitTransaction.Operation.BALANCE);
        FruitTransaction actual = null;
        for (FruitTransaction tr : actualList) {
            if (tr.getFruit().equals("banana")) {
                actual = tr;
            }
        }
        Assert.assertEquals(expected.getQuantity(), actual.getQuantity());
    }
}
