package core.basesyntax.model.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.ReturnOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_AMOUNT = 13;
    private static final int RETURN_AMOUNT = 3;
    private ReturnOperation returnOperation;
    private FruitDao fruitDao;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperation();
        fruitDao = new FruitDaoImpl();
    }

    @AfterEach
    void clearStorage() {
        Storage.storage.clear();
    }

    @Test
    void return_check_ok() {
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setName(FRUIT_NAME);
        balanceTransaction.setType(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setAmount(FRUIT_AMOUNT);
        returnOperation.handle(balanceTransaction);

        FruitTransaction returnTransaction = new FruitTransaction();
        returnTransaction.setName(FRUIT_NAME);
        returnTransaction.setType(FruitTransaction.Operation.RETURN);
        returnTransaction.setAmount(RETURN_AMOUNT);
        returnOperation.handle(returnTransaction);

        int expected = FRUIT_AMOUNT + RETURN_AMOUNT;
        assertEquals(expected, fruitDao.get(FRUIT_NAME));
    }
}
