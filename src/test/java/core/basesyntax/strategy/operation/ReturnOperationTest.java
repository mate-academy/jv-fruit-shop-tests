package core.basesyntax.strategy.operation;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private FruitsDao fruitsDao;
    private OperationHandler returnOperation;

    @BeforeEach
    public void setUp() throws Exception {
        fruitsDao = new FruitsDaoImpl();
        fruitsDao.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 400));
        fruitsDao.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        returnOperation = new ReturnOperation(fruitsDao);
    }

    @Test
    public void returnTransaction_ok() {
        returnOperation.transaction(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 5));
        FruitTransaction fruitTransaction = fruitsDao.get(new FruitTransaction("banana"));
        Integer expected = 405;
        Assertions.assertEquals(expected, fruitTransaction.getQuantity());
    }

    @AfterEach
    public void afterClass() {
        Storage.fruitsStorage.clear();
    }
}
