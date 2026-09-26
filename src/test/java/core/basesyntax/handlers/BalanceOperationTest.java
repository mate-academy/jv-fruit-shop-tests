package core.basesyntax.handlers;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.fruittransaction.FruitTransaction;
import core.basesyntax.fruittransaction.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalanceOperationTest {
    private static final String FRUIT_NAME_BANANA = "banana";
    private static final String FRUIT_NAME_APPLE = "apple";
    private static final String FRUIT_NAME_ORANGE = "orange";
    private static BalanceOperation bo;
    private static FruitsDao dao;

    @BeforeAll
    static void beforeAll() {
        dao = new FruitsDaoImpl();
        bo = new BalanceOperation(dao);
    }

    @Test
    void updateData_validData_ok() {
        FruitTransaction banana1 = new FruitTransaction(Operation.BALANCE,FRUIT_NAME_BANANA,1);
        FruitTransaction apple1 = new FruitTransaction(Operation.BALANCE,FRUIT_NAME_APPLE,2);
        FruitTransaction orange1 = new FruitTransaction(Operation.BALANCE,FRUIT_NAME_ORANGE,3);

        bo.updateData(banana1);
        bo.updateData(apple1);
        bo.updateData(orange1);

        assertEquals(1, dao.getFruitFromStorage(FRUIT_NAME_BANANA));
        assertEquals(2, dao.getFruitFromStorage(FRUIT_NAME_APPLE));
        assertEquals(3, dao.getFruitFromStorage(FRUIT_NAME_ORANGE));
    }

    @Test
    void updateData_nullData_notOk() {
        assertThrows(IllegalArgumentException.class,() -> bo.updateData(null));
    }
}