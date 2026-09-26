package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.fruittransaction.FruitTransaction;
import core.basesyntax.fruittransaction.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final String FRUIT_NAME_BANANA = "banana";
    private static final String FRUIT_NAME_APPLE = "apple";
    private static final String FRUIT_NAME_ORANGE = "orange";
    private static ReturnOperation ro;
    private static FruitsDao dao;

    @BeforeAll
    static void beforeAll() {
        dao = new FruitsDaoImpl();
        ro = new ReturnOperation(dao);
    }

    @Test
    void updateData_validData_ok() {
        FruitTransaction banana1 = new FruitTransaction(Operation.RETURN,FRUIT_NAME_BANANA,1);
        FruitTransaction banana2 = new FruitTransaction(Operation.RETURN,FRUIT_NAME_BANANA,2);
        FruitTransaction banana3 = new FruitTransaction(Operation.RETURN,FRUIT_NAME_BANANA,3);
        FruitTransaction apple1 = new FruitTransaction(Operation.RETURN,FRUIT_NAME_APPLE,1);
        FruitTransaction apple2 = new FruitTransaction(Operation.RETURN,FRUIT_NAME_APPLE,2);
        FruitTransaction orange1 = new FruitTransaction(Operation.RETURN,FRUIT_NAME_ORANGE,1);

        ro.updateData(banana1);
        ro.updateData(banana2);
        ro.updateData(banana3);
        ro.updateData(apple1);
        ro.updateData(apple2);
        ro.updateData(orange1);

        assertEquals(6, dao.getFruitFromStorage(FRUIT_NAME_BANANA));
        assertEquals(3, dao.getFruitFromStorage(FRUIT_NAME_APPLE));
        assertEquals(1, dao.getFruitFromStorage(FRUIT_NAME_ORANGE));
    }

    @Test
    void updateData_nullData_notOk() {
        assertThrows(IllegalArgumentException.class,() -> ro.updateData(null));
    }
}
