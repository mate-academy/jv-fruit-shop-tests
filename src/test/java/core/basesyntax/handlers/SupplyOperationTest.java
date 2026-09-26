package core.basesyntax.handlers;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.fruittransaction.FruitTransaction;
import core.basesyntax.fruittransaction.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplyOperationTest {
    private static final String FRUIT_NAME_BANANA = "banana";
    private static final String FRUIT_NAME_APPLE = "apple";
    private static final String FRUIT_NAME_ORANGE = "orange";
    private static SupplyOperation so;
    private static FruitsDao dao;

    @BeforeAll
    static void beforeAll() {
        dao = new FruitsDaoImpl();
        so = new SupplyOperation(dao);
    }

    @Test
    void updateData_validData_ok() {
        FruitTransaction banana1 = new FruitTransaction(Operation.SUPPLY,FRUIT_NAME_BANANA,1);
        FruitTransaction banana2 = new FruitTransaction(Operation.SUPPLY,FRUIT_NAME_BANANA,2);
        FruitTransaction banana3 = new FruitTransaction(Operation.SUPPLY,FRUIT_NAME_BANANA,3);
        FruitTransaction apple1 = new FruitTransaction(Operation.SUPPLY,FRUIT_NAME_APPLE,1);
        FruitTransaction apple2 = new FruitTransaction(Operation.SUPPLY,FRUIT_NAME_APPLE,2);
        FruitTransaction orange1 = new FruitTransaction(Operation.SUPPLY,FRUIT_NAME_ORANGE,1);

        so.updateData(banana1);
        so.updateData(banana2);
        so.updateData(banana3);
        so.updateData(apple1);
        so.updateData(apple2);
        so.updateData(orange1);

        assertEquals(6, dao.getFruitFromStorage(FRUIT_NAME_BANANA));
        assertEquals(3, dao.getFruitFromStorage(FRUIT_NAME_APPLE));
        assertEquals(1, dao.getFruitFromStorage(FRUIT_NAME_ORANGE));
    }

    @Test
    void updateData_nullData_notOk() {
        assertThrows(IllegalArgumentException.class,() -> so.updateData(null));
    }

}