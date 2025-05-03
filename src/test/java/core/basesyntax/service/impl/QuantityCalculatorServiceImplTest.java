package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.handler.BalanceOperationHandler;
import core.basesyntax.operation.handler.PurchaseOperationHandler;
import core.basesyntax.operation.handler.ReturnOperationHandler;
import core.basesyntax.operation.handler.SupplyOperationHandler;
import core.basesyntax.service.QuantityCalculatorService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class QuantityCalculatorServiceImplTest {
    private static QuantityCalculatorService quantityCalculatorService;
    private static Map<Operation, OperationHandler> operations;
    private static FruitStorageDao fruitStorageDao;

    @BeforeAll
    public static void beforeAll() {
        fruitStorageDao = new FruitStorageDaoImpl();
        operations = new HashMap<>();
        operations.put(Operation.BALANCE, new BalanceOperationHandler(fruitStorageDao));
        operations.put(Operation.SUPPLY, new SupplyOperationHandler(fruitStorageDao));
        operations.put(Operation.RETURN, new ReturnOperationHandler(fruitStorageDao));
        operations.put(Operation.PURCHASE, new PurchaseOperationHandler(fruitStorageDao));
        quantityCalculatorService = new QuantityCalculatorServiceImpl(operations);
    }

    @Test
    public void calcualteQuantityForFruits_validTransactions_ok() {
        FruitTransaction transactionFirst = new FruitTransaction();
        transactionFirst.setFruit("banana");
        transactionFirst.setOperation(Operation.BALANCE);
        transactionFirst.setAmount(100);

        FruitTransaction transactionSecond = new FruitTransaction();
        transactionSecond.setFruit("banana");
        transactionSecond.setOperation(Operation.PURCHASE);
        transactionSecond.setAmount(50);

        FruitTransaction transactionThird = new FruitTransaction();
        transactionThird.setFruit("apple");
        transactionThird.setOperation(Operation.BALANCE);
        transactionThird.setAmount(400);
        List<FruitTransaction> fruitTransactions = List.of(
                transactionFirst, transactionSecond, transactionThird);

        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("banana", 50);
        expectedStorage.put("apple", 400);
        quantityCalculatorService.calcualteQuantityForFruits(fruitTransactions);
        assertEquals(expectedStorage, fruitStorageDao.getAllFruit());
    }
}
