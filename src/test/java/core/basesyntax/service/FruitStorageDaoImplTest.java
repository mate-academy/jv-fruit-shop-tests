package core.basesyntax.service;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.impl.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitStorageDaoImplTest {

    private FruitStorageDao fruitStorageDao;

    @BeforeEach
    void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();
    }

    @AfterEach
    void clear() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    void addFruit_validTransaction_success() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 10);
        fruitStorageDao.addFruit(transaction);

        Integer actualApples = FruitStorage.fruitStorage.get("apple");
        Assertions.assertEquals(10, actualApples);
    }

    @Test
    void increaseQuantity_validTransaction_success() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 10);
        fruitStorageDao.addFruit(transaction);

        FruitTransaction additionalTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,"banana", 8);
        fruitStorageDao.increaseQuantity(additionalTransaction);

        Assertions.assertEquals(18, FruitStorage.fruitStorage.get("banana"));
    }

    @Test
    void decreaseQuantity_validTransaction_success() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,"orange", 10);
        fruitStorageDao.addFruit(transaction);

        FruitTransaction decreaseTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,"orange", 6);
        fruitStorageDao.decreaseQuantity(decreaseTransaction);

        Assertions.assertEquals(4, FruitStorage.fruitStorage.get("orange"));
    }

    @Test
    void getAllAsMap_nonEmptyStorage_success() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,"pear", 7);
        fruitStorageDao.addFruit(transaction);

        Map<String, Integer> storageMap = fruitStorageDao.getAllAsMap();
        Assertions.assertTrue(storageMap.containsKey("pear"));
        Assertions.assertEquals(7, storageMap.get("pear"));
    }
}
