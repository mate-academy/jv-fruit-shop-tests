package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dao.FruitStorageDao;
import dao.FruitStorageDaoImpl;
import java.util.ArrayList;
import model.Fruit;
import model.InputDataType;
import model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TransactionOperation;

class TransactionOperationImplTest {
    private TransactionOperation transactionOperation;
    private FruitStorageDao fruitStorageDao;

    @BeforeEach
    void setUp() {
        transactionOperation = new TransactionOperationImpl();
        fruitStorageDao = new FruitStorageDaoImpl();
        fruitStorageDao.set("banana", 0);
        fruitStorageDao.set("apple", 0);
    }

    @Test
    void transactionOperationValidInput_ok() {
        ArrayList<InputDataType> inputData = new ArrayList<>();
        InputDataType data1 = new InputDataType(Operation.BALANCE, new Fruit("apple", 5));
        InputDataType data2 = new InputDataType(Operation.SUPPLY, new Fruit("banana", 10));
        inputData.add(data1);
        inputData.add(data2);
        transactionOperation.execute(inputData, fruitStorageDao);

        Fruit apple = fruitStorageDao.getFruit("apple");
        assertEquals(5, apple.getQuantity(),
                "Actual and expected q-ty of apples should be the same");

        Fruit banana = fruitStorageDao.getFruit("banana");
        assertEquals(10, banana.getQuantity(),
                "Actual and expected q-ty of bananas should be equals");
    }
}
