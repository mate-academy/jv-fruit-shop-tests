package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitDao;
import core.basesyntax.db.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.exceptions.InvalidOperationException;
import core.basesyntax.transaction.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private OperationHandler operationHandler;
    private FruitDao fruitDao;

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
        fruitDao.saveOrUpdate("banana", 50);
        operationHandler = new ReturnOperation(fruitDao);
    }

    @AfterEach
    void tearDown() {
        FruitStorage.getFruitsStorage().clear();
    }

    @Test
    void apply_Ok() {
        FruitTransaction banana = new FruitTransaction();
        banana.setOperation(FruitTransaction.Operation.R);
        banana.setQuantity(10);
        banana.setFruitName("banana");
        operationHandler.apply(banana);
        fruitDao.saveOrUpdate("apple", 100);
        FruitTransaction apple = new FruitTransaction();
        apple.setFruitName("apple");
        apple.setOperation(FruitTransaction.Operation.R);
        apple.setQuantity(50);
        operationHandler.apply(apple);
        int expectedAppleQuantity = 150;
        int expectedBananaQuantity = 60;

        assertEquals(expectedBananaQuantity, fruitDao.getFruitQuantity("banana"));
        assertEquals(expectedAppleQuantity, fruitDao.getFruitQuantity("apple"));
    }

    @Test
    void apply_negativeQuantityOfFruits_NotOk() {
        FruitTransaction banana = new FruitTransaction();
        banana.setQuantity(-10);
        banana.setOperation(FruitTransaction.Operation.R);
        banana.setFruitName("banana");

        assertThrows(InvalidOperationException.class, () -> operationHandler.apply(banana));
    }

    @Test
    void apply_nullNullFruitName_NotOk() {
        FruitTransaction banana = new FruitTransaction();
        banana.setFruitName(null);
        banana.setOperation(FruitTransaction.Operation.P);
        banana.setQuantity(10);

        assertThrows(InvalidOperationException.class, () -> operationHandler.apply(banana));
    }
}
