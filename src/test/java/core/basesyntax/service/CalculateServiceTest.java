package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.CalculateServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.strategy.impl.OperationHandlerBalanceImpl;
import core.basesyntax.strategy.impl.OperationHandlerPurchaseImpl;
import core.basesyntax.strategy.impl.OperationHandlerReturnImpl;
import core.basesyntax.strategy.impl.OperationHandlerSupplyImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CalculateServiceTest {
    private static CalculateService calculateService;
    private static Storage storage;
    private static OperationHandlerStrategy operationHandlerStrategy;
    private static Map<FruitTransaction.Operation,
            OperationHandler> handlerMap;
    private static List<FruitTransaction> correct;
    private static final int BALANCE_INDEX = 0;
    private static final int TRANSACTION_INDEX = 2;
    private static final String FRUIT_BANANA = "banana";
    private static final String FRUIT_APPLE = "apple";
    private static final int QUANTITY_MORE_THAN_IN_STOCK = 1000;

    @BeforeClass
    public static void beforeAll() {
        storage = new Storage();
        handlerMap = Map.of(
                FruitTransaction.Operation.BALANCE,
                new OperationHandlerBalanceImpl(storage),
                FruitTransaction.Operation.PURCHASE,
                new OperationHandlerPurchaseImpl(storage),
                FruitTransaction.Operation.RETURN,
                new OperationHandlerReturnImpl(storage),
                FruitTransaction.Operation.SUPPLY,
                new OperationHandlerSupplyImpl(storage));
        operationHandlerStrategy = new OperationHandlerStrategy(handlerMap);
        calculateService = new CalculateServiceImpl(operationHandlerStrategy);
        correct = initCorrectFruitTransactionList();
    }

    @Test
    public void process_calculateTransactions_ok() {
        List<FruitTransaction> fruitTransactions = correct;
        calculateService.process(fruitTransactions);
        Integer expectedBananas = 252;
        Integer actualBananas = storage.STOCK_BALANCE.get(FRUIT_BANANA);
        Assert.assertEquals("Wrong result after calculating bananas. Must be "
                + expectedBananas + ". But current is "
                + actualBananas, expectedBananas, actualBananas);
        Integer expectedApples = 190;
        Integer actualApples = storage.STOCK_BALANCE.get(FRUIT_APPLE);
        Assert.assertEquals("Wrong result after calculating apples. Must be "
                + actualApples + ". But current is "
                + actualApples, expectedApples, actualApples);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void process_voidBalance_notOk() {
        List<FruitTransaction> fruitTransactions = correct;
        fruitTransactions.remove(BALANCE_INDEX);
        calculateService.process(fruitTransactions);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void process_negativeBalanceQuantity_notOk() {
        List<FruitTransaction> fruitTransactions = correct;
        FruitTransaction balanceWithNegativeQuantity
                = new FruitTransaction(FruitTransaction.Operation
                .BALANCE, "banana", -1);
        fruitTransactions.remove(BALANCE_INDEX);
        fruitTransactions.add(BALANCE_INDEX, balanceWithNegativeQuantity);
        calculateService.process(fruitTransactions);
    }

    @Test(expected = NullPointerException.class)
    public void process_wrongFruit_notOk() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>(correct);
        FruitTransaction wrongFruit
                = new FruitTransaction(FruitTransaction.Operation
                .SUPPLY, "wrongFruit", 1);
        fruitTransactions.add(TRANSACTION_INDEX, wrongFruit);
        calculateService.process(fruitTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void process_negativeSupply_notOk() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>(correct);
        FruitTransaction wrongFruit
                = new FruitTransaction(FruitTransaction.Operation
                .SUPPLY, "apple", -1);
        fruitTransactions.add(TRANSACTION_INDEX, wrongFruit);
        calculateService.process(fruitTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void process_negativeReturn_notOk() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>(correct);
        FruitTransaction wrongFruit
                = new FruitTransaction(FruitTransaction.Operation
                .RETURN, "apple", -1);
        fruitTransactions.add(TRANSACTION_INDEX, wrongFruit);
        calculateService.process(fruitTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void process_negativePurchase_notOk() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>(correct);
        FruitTransaction wrongFruit = new FruitTransaction(FruitTransaction.Operation
                .PURCHASE, FRUIT_APPLE, -1);
        fruitTransactions.add(TRANSACTION_INDEX, wrongFruit);
        calculateService.process(fruitTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void process_lackOfFruits_notOk() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>(correct);
        FruitTransaction wrongFruit = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, FRUIT_APPLE, QUANTITY_MORE_THAN_IN_STOCK);
        fruitTransactions.add(TRANSACTION_INDEX, wrongFruit);
        calculateService.process(fruitTransactions);
    }

    private static List<FruitTransaction> initCorrectFruitTransactionList() {
        return List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 120),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 200),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
    }
}
