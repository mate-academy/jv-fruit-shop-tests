package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final OperationHandler purchaseOperationHandler
            = new PurchaseOperationHandler(fruitDao);
    private final FruitTransaction fruit = new FruitTransaction();

    @BeforeEach
    public void init() {
        Storage.FRUIT_MAP.put("lemon", 50);
    }

    @Test
    void handle_validData_ok() {
        fruit.setOperation(FruitTransaction.Operation.PURCHASE);
        fruit.setFruitName("lemon");
        fruit.setFruitQuantity(32);
        purchaseOperationHandler.handle(fruit);
        String expected = "[lemon=18]";
        String actual = fruitDao.getAll().entrySet().toString();
        assertEquals(expected, actual);
    }

    @Test
    void handle_notValidData_notOk() {
        fruit.setOperation(FruitTransaction.Operation.PURCHASE);
        fruit.setFruitName("lemon");
        fruit.setFruitQuantity(-45);
        assertThrows(RuntimeException.class, () -> purchaseOperationHandler.handle(fruit));
    }

    @AfterEach
    public void clearStorageAfterEachTest() {
        Storage.FRUIT_MAP.clear();
    }
}
