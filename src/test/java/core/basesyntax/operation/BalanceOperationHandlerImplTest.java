package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitQuantityDao;
import core.basesyntax.dao.FruitQuantityDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerImplTest {
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_QUANTITY = 50;
    private static FruitQuantityDao fruitQuantityDao;
    private static OperationHandler balanceOperationHandler;
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        fruitQuantityDao = new FruitQuantityDaoImpl();
        transaction = new FruitTransaction(Operation.BALANCE,FRUIT_NAME, FRUIT_QUANTITY);
    }

    @BeforeEach
    void setUp() {
        balanceOperationHandler = new BalanceOperationHandlerImpl(fruitQuantityDao);
    }

    @Test
    void operate_withValidData_ok() {
        balanceOperationHandler.perform(transaction);
        assertEquals(FRUIT_QUANTITY, fruitQuantityDao.get(FRUIT_NAME));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitQuantity.clear();
    }
}
