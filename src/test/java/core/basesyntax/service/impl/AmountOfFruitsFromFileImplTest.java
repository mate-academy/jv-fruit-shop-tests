package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.AmountOfFruitsFromFile;
import core.basesyntax.service.CantWorkWithThisFileException;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operations.BalanceHandler;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseHandler;
import core.basesyntax.service.operations.ReturnHandler;
import core.basesyntax.service.operations.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AmountOfFruitsFromFileImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int NUMBER = 123;
    private AmountOfFruitsFromFile amount = null;

    public static OperationStrategy fillingConstructorWithOperationStrategy() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandler());

        return new OperationStrategyImpl(operationHandlerMap);
    }

    @BeforeEach
    void setUp() {
        amount = new AmountOfFruitsFromFileImpl(fillingConstructorWithOperationStrategy());
    }

    @Test
    void getAmountOfFruitsFromFile_EmptyFruitsList_NotOk() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(Operation.BALANCE,
                APPLE, NUMBER));
        fruitTransactions.add(new FruitTransaction(Operation.PURCHASE,
                BANANA, NUMBER));
        fruitTransactions.add(new FruitTransaction(Operation.RETURN,
                APPLE, NUMBER));
        fruitTransactions.add(new FruitTransaction(Operation.SUPPLY,
                BANANA, NUMBER));
        assertThrows(CantWorkWithThisFileException.class,
                () -> amount.getAmountOfFruitsFromFile(null, fruitTransactions),
                "Fruit List can't be null when FT is full");
    }

    @Test
    void getAmountOfFruitsFromFile_EmptyFruitTransactionList_NotOk() {
        List<String> fruits = new ArrayList<>();
        fruits.add(BANANA);
        fruits.add(APPLE);
        assertThrows(CantWorkWithThisFileException.class,
                () -> amount.getAmountOfFruitsFromFile(fruits, null),
                "Fruit List can't be null when FT is full");
    }

    @Test
    void getAmountOfFruitsFromFile_OutputMakeQuantityList_Ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(
                Operation.BALANCE,BANANA, 20));
        fruitTransactions.add(new FruitTransaction(
                Operation.BALANCE,APPLE, 100));
        fruitTransactions.add(new FruitTransaction(
                Operation.SUPPLY,BANANA, 100));
        fruitTransactions.add(new FruitTransaction(
                Operation.PURCHASE,BANANA, 13));
        fruitTransactions.add(new FruitTransaction(
                Operation.RETURN,APPLE, 10));
        fruitTransactions.add(new FruitTransaction(
                Operation.PURCHASE,APPLE, 20));
        fruitTransactions.add(new FruitTransaction(
                Operation.PURCHASE,BANANA, 5));
        fruitTransactions.add(new FruitTransaction(
                Operation.SUPPLY,BANANA, 50));

        List<String> fruits = new ArrayList<>();
        fruits.add(APPLE);
        fruits.add(BANANA);

        List<Integer> expectedQuantityList = new ArrayList<>();
        expectedQuantityList.add(90);
        expectedQuantityList.add(152);
        List<Integer> actual = amount.getAmountOfFruitsFromFile(
                fruits, fruitTransactions);
        assertEquals(expectedQuantityList, actual);
    }
}
