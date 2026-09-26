package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.fruittransaction.FruitTransaction;
import core.basesyntax.fruittransaction.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final String FRUIT_NAME_BANANA = "banana";
    private static final String FRUIT_NAME_APPLE = "apple";
    private static final String FRUIT_NAME_ORANGE = "orange";
    private static PurchaseOperation po;
    private static FruitsDao dao;

    @BeforeAll
    static void beforeAll() {
        dao = new FruitsDaoImpl();
        po = new PurchaseOperation(dao);
    }

    @BeforeEach
    void setUp() {
        dao.addFruitToStorage(FRUIT_NAME_BANANA, 6);
        dao.addFruitToStorage(FRUIT_NAME_APPLE, 3);
        dao.addFruitToStorage(FRUIT_NAME_ORANGE, 1);
    }

    @Test
    void updateData_validData_ok() {
        final FruitTransaction banana =
                new FruitTransaction(Operation.PURCHASE, FRUIT_NAME_BANANA, 6);
        final FruitTransaction apple =
                new FruitTransaction(Operation.PURCHASE, FRUIT_NAME_APPLE, 3);
        final FruitTransaction orange =
                new FruitTransaction(Operation.PURCHASE, FRUIT_NAME_ORANGE, 1);

        po.updateData(banana);
        po.updateData(apple);
        po.updateData(orange);

        assertEquals(0, dao.getFruitFromStorage(FRUIT_NAME_BANANA));
        assertEquals(0, dao.getFruitFromStorage(FRUIT_NAME_APPLE));
        assertEquals(0, dao.getFruitFromStorage(FRUIT_NAME_ORANGE));
    }

    @Test
    void updateData_invalidData_notOk() {
        FruitTransaction banana = new FruitTransaction(Operation.PURCHASE, FRUIT_NAME_BANANA, 7);
        FruitTransaction apple = new FruitTransaction(Operation.PURCHASE, FRUIT_NAME_APPLE, 4);
        FruitTransaction orange = new FruitTransaction(Operation.PURCHASE, FRUIT_NAME_ORANGE, 2);

        assertThrows(IllegalArgumentException.class, () -> po.updateData(banana));
        assertThrows(IllegalArgumentException.class, () -> po.updateData(apple));
        assertThrows(IllegalArgumentException.class, () -> po.updateData(orange));
    }

    @Test
    void updateData_nullData_notOk() {
        assertThrows(IllegalArgumentException.class, () -> po.updateData(null));
    }
}
