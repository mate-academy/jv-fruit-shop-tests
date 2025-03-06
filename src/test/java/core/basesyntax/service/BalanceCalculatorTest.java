package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationsList;
import core.basesyntax.service.impl.BalanceCalculatorImpl;
import core.basesyntax.service.impl.BalanceHandler;
import core.basesyntax.service.impl.PurchaseHandler;
import core.basesyntax.service.impl.ReturnHandler;
import core.basesyntax.service.impl.SupplyHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceCalculatorTest {
    private BalanceCalculatorImpl balanceCalculator;
    private OperationStrategy strategy;

    @BeforeEach
    void setUp() {
        OperationHandler balance = new BalanceHandler();
        OperationHandler purchase = new PurchaseHandler();
        OperationHandler returner = new ReturnHandler();
        OperationHandler supply = new SupplyHandler();
        strategy = new OperationStrategy(Map.of(
                OperationsList.BALANCE, balance,
                OperationsList.PURCHASE, purchase,
                OperationsList.RETURN, returner,
                OperationsList.SUPPLY, supply));
        balanceCalculator = new BalanceCalculatorImpl(strategy);
    }

    @Test
    void purchase_Operation_IsOk() {
        String fruit = "apple";
        OperationsList operation = OperationsList.PURCHASE;
        int quantity = 10;
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        List<FruitTransaction> list = new ArrayList<>();
        list.add(fruitTransaction);
        assertEquals(-10, balanceCalculator.update(list).get(fruit));
        Storage.fruitsStorage.clear();
    }

    @Test
    void balance_Operation_IsOk() {
        String fruit = "apple";
        OperationsList operation = OperationsList.BALANCE;
        int quantity = 10;
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        List<FruitTransaction> list = new ArrayList<>();
        list.add(fruitTransaction);
        assertEquals(10, balanceCalculator.update(list).get(fruit));
        Storage.fruitsStorage.clear();
    }

    @Test
    void return_Operation_IsOk() {
        String fruit = "apple";
        OperationsList operation = OperationsList.RETURN;
        int quantity = 10;
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        List<FruitTransaction> list = new ArrayList<>();
        list.add(fruitTransaction);
        assertEquals(10, balanceCalculator.update(list).get(fruit));
        Storage.fruitsStorage.clear();
    }

    @Test
    void supply_Operation_IsOk() {
        String fruit = "apple";
        OperationsList operation = OperationsList.SUPPLY;
        int quantity = 10;
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        List<FruitTransaction> list = new ArrayList<>();
        list.add(fruitTransaction);
        assertEquals(10, balanceCalculator.update(list).get(fruit));
        Storage.fruitsStorage.clear();
    }
}
