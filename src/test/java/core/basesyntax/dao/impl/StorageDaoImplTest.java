package core.basesyntax.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitModel;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    void testAddFruit_ok() {
        FruitModel fruitModel = FruitModel.BANANA;
        int initialAmount = 10;
        String expectedMessage = fruitModel + " added to the storage. Amount: " + initialAmount;
        String actualMessage = storageDao.addFruit(fruitModel, initialAmount);
        assertEquals(expectedMessage, actualMessage);
        assertEquals(initialAmount, (int) Storage.fruitStorage.get(fruitModel));
    }

    @Test
    void testAddSeveralFruits_ok() {
        FruitModel[] fruitModels = {FruitModel.BANANA, FruitModel.APPLE, FruitModel.PINEAPPLE,
                FruitModel.LIME, FruitModel.PEACH};
        int[] initialAmounts = {3, 4, 5, 4, 3};
        Map<FruitModel, Integer> expectedStorageState = new HashMap<>();
        for (int i = 0; i < fruitModels.length; i++) {
            FruitModel fruitModel = fruitModels[i];
            int initialAmount = initialAmounts[i];
            expectedStorageState.put(fruitModel, initialAmount);
            storageDao.addFruit(fruitModel, initialAmount);
        }
        Map<FruitModel, Integer> actualStorageState = Storage.fruitStorage;
        assertEquals(expectedStorageState.size(), actualStorageState.size());
        for (Map.Entry<FruitModel, Integer> entry : expectedStorageState.entrySet()) {
            FruitModel fruitModel = entry.getKey();
            int expectedAmount = entry.getValue();
            int actualAmount = actualStorageState.getOrDefault(fruitModel, 0);
            assertEquals(expectedAmount, actualAmount);
        }
    }

    @Test
    void testUpdateFruitAmount_ok() {
        FruitModel fruitModel = FruitModel.APPLE;
        int initialAmount = 20;
        Storage.fruitStorage.put(fruitModel, initialAmount);
        int consumedAmount = 10;
        int newAmount = storageDao.updateFruitAmount(fruitModel, consumedAmount);
        assertEquals(initialAmount - consumedAmount, newAmount);
        assertEquals(initialAmount - consumedAmount, (int) Storage.fruitStorage.get(fruitModel));
    }

    @Test
    void testUpdateSeveralFruitsAmount_ok() {
        Storage.fruitStorage.put(FruitModel.BANANA, 10);
        Storage.fruitStorage.put(FruitModel.APPLE, 20);
        Storage.fruitStorage.put(FruitModel.PINEAPPLE, 30);
        storageDao.updateFruitAmount(FruitModel.BANANA, 3);
        storageDao.updateFruitAmount(FruitModel.APPLE, 4);
        storageDao.updateFruitAmount(FruitModel.PINEAPPLE, 5);
        assertEquals(7, (int) Storage.fruitStorage.get(FruitModel.BANANA));
        assertEquals(16, (int) Storage.fruitStorage.get(FruitModel.APPLE));
        assertEquals(25, (int) Storage.fruitStorage.get(FruitModel.PINEAPPLE));
    }

    @Test
    void testUpdateFruitAmountToZero_ok() {
        FruitModel lime = FruitModel.LIME;
        storageDao.addFruit(lime, 5);
        int newAmount = storageDao.updateFruitAmount(lime, 5);
        assertEquals(0, newAmount);
        assertEquals(0, Storage.fruitStorage.get(lime));
    }

    @Test
    void testUpdateFruitAmount_getNotValidAmount_notOk() {
        FruitModel pineapple = FruitModel.PINEAPPLE;
        storageDao.addFruit(pineapple, 5);
        IllegalArgumentException firstException = assertThrows(IllegalArgumentException.class,
                () -> storageDao.updateFruitAmount(pineapple, 6));
        assertEquals("There are not enough fruits in the storage. Current amount is: "
                + Storage.fruitStorage.get(pineapple), firstException.getMessage());
        IllegalArgumentException secondException =
                assertThrows(IllegalArgumentException.class,
                        () -> storageDao.updateFruitAmount(pineapple, 10));
        assertEquals("There are not enough fruits in the storage. Current amount is: "
                + Storage.fruitStorage.get(pineapple), secondException.getMessage());
    }

    @Test
    void testAddFruitWithZeroAmount_ok() {
        FruitModel banana = FruitModel.BANANA;
        storageDao.addFruit(banana, 0);
        assertEquals(0, Storage.fruitStorage.get(banana));
    }

    @AfterEach
    void cleanUp() {
        Storage.fruitStorage.clear();
    }
}
