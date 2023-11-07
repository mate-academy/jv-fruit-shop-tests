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

class PurchaseOperationHandlerImplTest {
    private static final String FRUIT_NAME = "banana";
    private static final int START_FRUIT_QUANTITY = 50;
    private static final int OPERATION_FRUIT_QUANTITY = 20;
    private static FruitQuantityDao fruitQuantityDao;
    private static OperationHandler balanceOperationHandler;
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        fruitQuantityDao = new FruitQuantityDaoImpl();
        transaction = new FruitTransaction(Operation.PURCHASE,FRUIT_NAME, OPERATION_FRUIT_QUANTITY);
    }

    @BeforeEach
    void setUp() {
        balanceOperationHandler = new PurchaseOperationHandlerImpl(fruitQuantityDao);
    }

    @Test
    void operate_withValidData_ok() {
        fruitQuantityDao.add(FRUIT_NAME, START_FRUIT_QUANTITY);
        balanceOperationHandler.perform(transaction);
        assertEquals(START_FRUIT_QUANTITY - OPERATION_FRUIT_QUANTITY,
                fruitQuantityDao.get(FRUIT_NAME));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitQuantity.clear();
    }
}
