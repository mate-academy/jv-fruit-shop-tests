package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.AmountOfFruitsFromFile;
import core.basesyntax.service.CantWorkWithThisFileException;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operations.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AmountOfFruitsFromFileImplTest {
    public static OperationStrategy fillingConstructorWithOperationStrategy() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandler());

        return new OperationStrategyImpl(operationHandlerMap);
    }
    AmountOfFruitsFromFile amount = null;
    @BeforeEach
    void setUp() {
        amount = new AmountOfFruitsFromFileImpl(fillingConstructorWithOperationStrategy());
    }

    @Test
    void getAmountOfFruitsFromFile_EmptyFruitsList_NotOk() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(Operation.BALANCE,
                "apple", 45));
        fruitTransactions.add(new FruitTransaction(Operation.PURCHASE,
                "banana", 143));
        fruitTransactions.add(new FruitTransaction(Operation.RETURN,
                "apple", 12));
        fruitTransactions.add(new FruitTransaction(Operation.SUPPLY,
                "banana", 67));
        assertThrows(CantWorkWithThisFileException.class ,
                () -> amount.getAmountOfFruitsFromFile(null, fruitTransactions),
                "Fruit List can't be null when FT is full");
    }
    @Test
    void getAmountOfFruitsFromFile_EmptyFruitTransactionList_NotOk() {
        List<String> fruits = new ArrayList<>();
        fruits.add("banana");
        fruits.add("apple");
        assertThrows(CantWorkWithThisFileException.class ,
                () -> amount.getAmountOfFruitsFromFile(fruits, null),
                "Fruit List can't be null when FT is full");
    }
}
