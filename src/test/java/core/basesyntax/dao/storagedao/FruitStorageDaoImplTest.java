package core.basesyntax.dao.storagedao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.operation.Operation;
import core.basesyntax.dao.transaction.FruitTransaction;
import core.basesyntax.db.FruitStorage;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageDaoImplTest {
    private FruitStorageDaoImpl fruitStorageDao;

    @BeforeEach
    void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();
        FruitStorage.fruitDB.clear();
    }

    @Test
    void testAdd_Ok() {
        //Arrange
        FruitTransaction transaction1 = new FruitTransaction(Operation.BALANCE, "apple", 34);

        //Act
        fruitStorageDao.add(transaction1);
        List<FruitTransaction> resultList = fruitStorageDao.getAllTransaction();

        //Assert
        assertEquals(1, resultList.size());
    }

    @Test
    void testGetAllTransaction_Ok() {
        //Arrange
        FruitTransaction transaction1 = new FruitTransaction(Operation.BALANCE, "apple", 34);
        FruitTransaction transaction2 = new FruitTransaction(Operation.PURCHASE, "banana", 12);
        FruitTransaction transaction3 = new FruitTransaction(Operation.SUPPLY, "lemon", 90);

        //Act
        fruitStorageDao.add(transaction1);
        fruitStorageDao.add(transaction2);
        fruitStorageDao.add(transaction3);
        List<FruitTransaction> resultList = fruitStorageDao.getAllTransaction();

        //Assert
        assertEquals(3, resultList.size());
        assertEquals(transaction1, resultList.get(0));
        assertEquals(transaction2, resultList.get(1));
        assertEquals(transaction3, resultList.get(2));
    }
}
