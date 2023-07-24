package service.impl;

import dao.FruitStorageDao;
import dao.FruitStorageDaoImpl;
import model.Fruit;
import model.InputDataType;
import model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TransactionOperation;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TransactionOperationImplTest {
    private TransactionOperation transactionOperation;
    private FruitStorageDao fruitStorageDao;

    @BeforeEach
    void setUp() {
        transactionOperation = new TransactionOperationImpl();
        fruitStorageDao = new FruitStorageDaoImpl();
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