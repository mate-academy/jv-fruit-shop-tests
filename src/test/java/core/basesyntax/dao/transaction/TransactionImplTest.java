package core.basesyntax.dao.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.operation.Operation;
import core.basesyntax.dao.storagedao.FruitStorageDao;
import core.basesyntax.dao.storagedao.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionImplTest {
    private Transaction transaction;
    private List<String> testlist;
    private FruitStorageDao fruitStorageDao;

    @BeforeEach
    void setUp() {
        transaction = new TransactionImpl();
        testlist = new ArrayList<>();
        fruitStorageDao = new FruitStorageDaoImpl();
        FruitStorage.fruitDB.clear();
    }

    @Test
    void testGetTransactionList() {
        //Arrange
        String testStr1 = "b,apple,20";
        testlist.add(testStr1);

        //Act
        transaction.getTransactionList(testlist);
        List<FruitTransaction> resultList = fruitStorageDao.getAllTransaction();

        //Assert
        assertEquals(1, resultList.size());
    }

    @Test
    void testGetTransactionList_InValidLength() {
        //Arrange
        String testStr1 = "b,apple,20,40";
        testlist.add(testStr1);

        //Act
        transaction.getTransactionList(testlist);
        List<FruitTransaction> resultList = fruitStorageDao.getAllTransaction();

        //Assert
        assertEquals(0, resultList.size());
    }

    @Test
    void testGetTransactionList_InValidOperation() {
        //Arrange
        String testStr1 = "w,apple,20";
        testlist.add(testStr1);

        //Act
        transaction.getTransactionList(testlist);
        List<FruitTransaction> resultList = fruitStorageDao.getAllTransaction();

        //Assert
        assertEquals(0, resultList.size());
    }

    @Test
    void testGetTransactionList_NullLine() {
        //Arrange
        String testStr1 = null;
        testlist.add(testStr1);

        //Act
        transaction.getTransactionList(testlist);
        List<FruitTransaction> resultList = fruitStorageDao.getAllTransaction();

        //Assert
        assertEquals(0, resultList.size());
    }

    @Test
    void testGetTransactionList_FruitTransaction() {
        //Arrange
        String testStr1 = "b,apple,20";
        testlist.add(testStr1);
        FruitTransaction assertFruitTransaction
                = new FruitTransaction(Operation.BALANCE, "apple", 20);

        //Act
        transaction.getTransactionList(testlist);
        List<FruitTransaction> resultList = fruitStorageDao.getAllTransaction();

        //Assert
        assertEquals(assertFruitTransaction.getOperation(), resultList.get(0).getOperation());
        assertEquals(assertFruitTransaction.getName(), resultList.get(0).getName());
        assertEquals(assertFruitTransaction.getQuantity(), resultList.get(0).getQuantity());
    }
}
