package core.basesyntax.dao.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.operation.Operation;
import core.basesyntax.dao.storagedao.FruitStorageDaoImpl;
import core.basesyntax.dao.transaction.FruitTransaction;
import core.basesyntax.db.FruitStorage;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private FruitStorageDaoImpl fruitStorageDao;
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();
        reportCreator = new ReportCreatorImpl();
        FruitStorage.fruitDB.clear();
    }

    @Test
    void testCreateReport_AddOneAdditionalTransaction_Ok() {
        //Arrange
        FruitTransaction fruitTransaction1
                = new FruitTransaction(Operation.BALANCE, "apple", 20);

        fruitStorageDao.add(fruitTransaction1);

        //Act
        Map<String,Integer> result = reportCreator.createReport();

        //Assert
        assertEquals(1, result.size());
        assertEquals(20, result.get("apple"));
    }

    @Test
    void testCreateReport_AddTwoAdditionalTransaction_Same_Ok() {
        //Arrange
        FruitTransaction fruitTransaction1
                = new FruitTransaction(Operation.BALANCE, "apple", 20);
        FruitTransaction fruitTransaction2
                = new FruitTransaction(Operation.BALANCE, "apple", 30);

        fruitStorageDao.add(fruitTransaction1);
        fruitStorageDao.add(fruitTransaction2);

        //Act
        Map<String,Integer> result = reportCreator.createReport();

        //Assert
        assertEquals(1, result.size());
        assertEquals(50, result.get("apple"));
    }

    @Test
    void testCreateReport_AddTwoAdditionalTransaction_different_Ok() {
        //Arrange
        FruitTransaction fruitTransaction1
                = new FruitTransaction(Operation.BALANCE, "apple", 20);
        FruitTransaction fruitTransaction2
                = new FruitTransaction(Operation.BALANCE, "banana", 30);

        fruitStorageDao.add(fruitTransaction1);
        fruitStorageDao.add(fruitTransaction2);

        //Act
        Map<String,Integer> result = reportCreator.createReport();

        //Assert
        assertEquals(2, result.size());
        assertEquals(20, result.get("apple"));
        assertEquals(30, result.get("banana"));
    }

    @Test
    void testCreateReport_AddOneNegativeTransaction_NotOk() {
        //Arrange
        FruitTransaction fruitTransaction1
                = new FruitTransaction(Operation.PURCHASE, "apple", 20);
        fruitStorageDao.add(fruitTransaction1);

        //Assert
        RuntimeException exception
                = assertThrows(RuntimeException.class, () -> reportCreator.createReport());

        assertEquals("Don't have apple", exception.getMessage());
    }

    @Test
    void testCreateReport_AddOneNegativeTransaction_moreOnDB_NotOk() {
        //Arrange
        FruitTransaction fruitTransaction1
                = new FruitTransaction(Operation.BALANCE, "apple", 20);

        FruitTransaction fruitTransaction2
                = new FruitTransaction(Operation.PURCHASE, "apple", 30);

        fruitStorageDao.add(fruitTransaction1);
        fruitStorageDao.add(fruitTransaction2);

        //Assert
        RuntimeException exception
                = assertThrows(RuntimeException.class, () -> reportCreator.createReport());
        assertEquals("Don't have 30 apple", exception.getMessage());
    }
}
