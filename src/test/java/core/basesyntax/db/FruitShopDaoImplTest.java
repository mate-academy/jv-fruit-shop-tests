package core.basesyntax.db;

import core.basesyntax.service.CantWorkWithThisFileException;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FruitShopDaoImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private FruitShopDao fruitShopDao;
    private Map<String, Integer> fruitStorage;

    @BeforeEach
    void setUp() {
        fruitShopDao = new FruitShopDaoImpl();
        fruitStorage = Storage.fruitStorage;
        fruitStorage.clear();
    }

    @Test
    void getStorageSize_EqualsInClassAndInStorage_Ok() {
        int storageSize = fruitShopDao.getStorageSize();
        int size = fruitStorage.size();
        assertEquals(size,storageSize);
    }

    @Test
    void getAllFruitsWithQuantities_GetListWithCorrectOutput_Ok() {
        fruitShopDao.addFruitAndQuantity(APPLE, 1);
        fruitShopDao.addFruitAndQuantity(BANANA, 2);

        List<String> expected = new ArrayList<>();
        expected.add(BANANA + ", 2");
        expected.add(APPLE + ", 1");

        List<String> result = fruitShopDao.getAllFruitsWithQuantities();
            assertEquals(expected,result);

    }

    @Test
    void getAllFruitsWithQuantities_NullInMap_NotOk() {
        fruitShopDao.addFruitAndQuantity(BANANA, 1);
        fruitShopDao.addFruitAndQuantity(null, 2);

        assertThrows(CantWorkWithThisFileException.class,
                () -> fruitShopDao.getAllFruitsWithQuantities(),
                "some value is null");
    }

    @Test
    void addFruitAndQuantity_FruitAndQuantityIsInStorage_Ok() {
        fruitShopDao.addFruitAndQuantity(BANANA, 1);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(BANANA, 1);
        assertEquals(expected, fruitStorage);
    }

}
