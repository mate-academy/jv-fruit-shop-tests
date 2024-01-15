package core.basesyntax.model.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.QuantityOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuantityOperationTest {
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_AMOUNT = 13;
    private static final int EXPECTED = 13;
    private FruitDao fruitDao;
    private QuantityOperation quantityOperation;

    @BeforeEach
    void setup() {
        fruitDao = new FruitDaoImpl();
        quantityOperation = new QuantityOperation();
    }

    @AfterEach
    void clearStorage() {
        Storage.storage.clear();
    }

    @Test
    void quantity_check_ok() {
        FruitTransaction fruitTransactionBalance = new FruitTransaction();
        fruitTransactionBalance.setName(FRUIT_NAME);
        fruitTransactionBalance.setType(FruitTransaction.Operation.BALANCE);
        fruitTransactionBalance.setAmount(FRUIT_AMOUNT);
        quantityOperation.handle(fruitTransactionBalance);
        assertEquals(EXPECTED, fruitDao.get(FRUIT_NAME));
    }
}
