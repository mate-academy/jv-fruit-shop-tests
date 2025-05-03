package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.InvalidOperationException;
import core.basesyntax.exceptions.NoSuchFruitInStorageException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private FruitDao fruitDao;

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.getFruitsStorage().clear();
    }

    @Test
    void saveOrUpdate_Ok() {
        String fruitName = "banana";
        Integer quantity = 2;
        fruitDao.saveOrUpdate(fruitName, quantity);

        int expectedSize = 1;
        int actualSize = FruitStorage.getFruitsStorage().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void saveOrUpdate_nullFruitName_NotOk() {
        String fruitName = null;
        Integer quantity = 2;

        assertThrows(InvalidOperationException.class,
                () -> fruitDao.saveOrUpdate(fruitName, quantity));
    }

    @Test
    void saveOrUpdate_nullQuantity_NotOk() {
        String fruitName = "banana";
        Integer quantity = null;

        assertThrows(InvalidOperationException.class,
                () -> fruitDao.saveOrUpdate(fruitName, quantity));
    }

    @Test
    void saveOrUpdate_zeroQuantity_NotOk() {
        String fruitName = "banana";
        Integer quantity = 0;

        assertThrows(InvalidOperationException.class,
                () -> fruitDao.saveOrUpdate(fruitName, quantity));
    }

    @Test
    void saveOrUpdate_negativeQuantity_NotOk() {
        String fruitName = "banana";
        Integer quantity = -10;

        assertThrows(InvalidOperationException.class,
                () -> fruitDao.saveOrUpdate(fruitName, quantity));
    }

    @Test
    void saveOrUpdate_emptyFruitName_NotOk() {
        String fruitName = "";
        Integer quantity = 2;

        assertThrows(InvalidOperationException.class,
                () -> fruitDao.saveOrUpdate(fruitName, quantity));
    }

    @Test
    void getFruitQuantity_Ok() {
        String fruitName = "banana";
        Integer quantity = 2;
        fruitDao.saveOrUpdate(fruitName, quantity);
        int expectedQuantity = 2;
        Integer actualQuantity = fruitDao.getFruitQuantity("banana");

        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void getFruitQuantity_NullFruitName_NotOk() {
        assertThrows(InvalidOperationException.class,
                () -> fruitDao.getFruitQuantity(null));
    }

    @Test
    void getFruitQuantity_EmptyFruitName_NotOk() {
        assertThrows(InvalidOperationException.class,
                () -> fruitDao.getFruitQuantity(""));
    }

    @Test
    void getFruitQuantity_NoSuchFruitInStorage_NotOk() {
        assertThrows(NoSuchFruitInStorageException.class,
                () -> fruitDao.getFruitQuantity("test"));
    }
}
