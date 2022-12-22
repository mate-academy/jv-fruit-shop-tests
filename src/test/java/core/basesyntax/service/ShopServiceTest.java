package core.basesyntax.service;

import static org.hamcrest.CoreMatchers.is;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.activity.ActivityHandler;
import core.basesyntax.service.activity.BalanceActivityHandler;
import core.basesyntax.service.activity.PurchaseActivityHandler;
import core.basesyntax.service.activity.ReturnActivityHandler;
import core.basesyntax.service.activity.SupplyActivityHandler;
import core.basesyntax.service.impl.ActivityStrategyImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopServiceTest {
    private static final Fruit FRUIT = new Fruit("apple");
    private static ShopService shopService;
    private static FruitTransaction balanceTransaction;
    private static FruitTransaction returnTransaction;
    private static FruitTransaction supplyTransaction;
    private static FruitTransaction purchaseTransaction;

    @BeforeClass
    public static void beforeClass() {
        FruitStorageDao fruitStorageDao = new FruitStorageDaoImpl();
        Map<FruitTransaction.Operation, ActivityHandler> operationActivityHandlerMap =
                new HashMap<>();
        operationActivityHandlerMap.put(
                FruitTransaction.Operation.BALANCE, new BalanceActivityHandler(fruitStorageDao));
        operationActivityHandlerMap.put(
                FruitTransaction.Operation.PURCHASE, new PurchaseActivityHandler(fruitStorageDao));
        operationActivityHandlerMap.put(
                FruitTransaction.Operation.SUPPLY, new SupplyActivityHandler(fruitStorageDao));
        operationActivityHandlerMap.put(
                FruitTransaction.Operation.RETURN, new ReturnActivityHandler(fruitStorageDao));
        ActivityStrategy activityStrategy = new ActivityStrategyImpl(operationActivityHandlerMap);
        shopService = new ShopServiceImpl(activityStrategy);
        balanceTransaction = new FruitTransaction();
        balanceTransaction.setFruit(FRUIT);
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setAmount(50);
        returnTransaction = new FruitTransaction();
        returnTransaction.setFruit(FRUIT);
        returnTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnTransaction.setAmount(20);
        supplyTransaction = new FruitTransaction();
        supplyTransaction.setFruit(FRUIT);
        supplyTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyTransaction.setAmount(30);
        purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setFruit(FRUIT);
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setAmount(100);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void processTransactions_nullInput_notOk() {
        try {
            shopService.processTransactions(null);
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Input value can't be null"));
        }
    }

    @Test(expected = RuntimeException.class)
    public void processTransactions_inputListWithNull_notOk() {
        List<FruitTransaction> nullList = new ArrayList<>();
        nullList.add(null);
        shopService.processTransactions(nullList);
    }

    @Test(expected = RuntimeException.class)
    public void processTransactions_inputListWithNullFruitInTransaction_notOk() {
        FruitTransaction nullFruitTransaction = new FruitTransaction();
        nullFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        nullFruitTransaction.setAmount(0);
        List<FruitTransaction> nullFruitTransactionsList = new ArrayList<>();
        nullFruitTransactionsList.add(nullFruitTransaction);
        shopService.processTransactions(nullFruitTransactionsList);
    }

    @Test(expected = RuntimeException.class)
    public void processTransactions_inputListWithNullOperationInTransaction_notOk() {
        FruitTransaction nullAmountTransaction = new FruitTransaction();
        nullAmountTransaction.setFruit(FRUIT);
        nullAmountTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        List<FruitTransaction> nullAmountTransactionsList = new ArrayList<>();
        nullAmountTransactionsList.add(nullAmountTransaction);
        shopService.processTransactions(nullAmountTransactionsList);
    }

    @Test(expected = RuntimeException.class)
    public void processTransactions_inputListWithNullAmountInTransaction_notOk() {
        FruitTransaction nullOperationTransaction = new FruitTransaction();
        nullOperationTransaction.setFruit(FRUIT);
        nullOperationTransaction.setAmount(100);
        List<FruitTransaction> nullOperationTransactionsList = new ArrayList<>();
        nullOperationTransactionsList.add(nullOperationTransaction);
        shopService.processTransactions(nullOperationTransactionsList);
    }

    @Test
    public void processTransactions_inputListWithRepeatedBalanceTransactions_notOK() {
        List<FruitTransaction> balanceTwoTimes = new ArrayList<>();
        balanceTwoTimes.add(balanceTransaction);
        balanceTwoTimes.add(balanceTransaction);
        try {
            shopService.processTransactions(balanceTwoTimes);
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Balance information about fruit "
                    + "already has, fruit: " + balanceTransaction.getFruit().getName()));
        }
    }

    @Test
    public void processTransactions_inputListWithPurchaseMoreThanBalance_notOk() {
        List<FruitTransaction> purchaseMoreThanBalance = new ArrayList<>();
        purchaseMoreThanBalance.add(balanceTransaction);
        purchaseMoreThanBalance.add(purchaseTransaction);
        try {
            shopService.processTransactions(purchaseMoreThanBalance);
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Missing information about fruit balance "
                    + "or wrong purchase amount, fruit: " + balanceTransaction.getFruit().getName()
                    + ", amount: " + balanceTransaction.getAmount()
                    + ", purchase: " + purchaseTransaction.getAmount()));
        }
    }

    @Test
    public void processTransactions_inputListWithPurchaseWithoutBalance_notOk() {
        List<FruitTransaction> purchaseWithoutBalanceInfo = new ArrayList<>();
        purchaseWithoutBalanceInfo.add(purchaseTransaction);
        try {
            shopService.processTransactions(purchaseWithoutBalanceInfo);
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Missing information about fruit balance "
                    + "or wrong purchase amount, fruit: " + purchaseTransaction.getFruit().getName()
                    + ", amount: " + Storage.fruitStorage.get(purchaseTransaction.getFruit())
                    + ", purchase: " + purchaseTransaction.getAmount()));
        }
    }

    @Test
    public void processTransactions_inputListWithReturnWithoutBalance_notOk() {
        List<FruitTransaction> returnWithoutBalanceInfo = new ArrayList<>();
        returnWithoutBalanceInfo.add(returnTransaction);
        try {
            shopService.processTransactions(returnWithoutBalanceInfo);
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Missing balance info about of fruit "
                    + "for return operation, fruit: " + returnTransaction.getFruit().getName()));
        }
    }

    @Test
    public void processTransactions_inputListWithSupplyWithoutBalance_notOk() {
        List<FruitTransaction> supplyWithoutBalanceInfo = new ArrayList<>();
        supplyWithoutBalanceInfo.add(supplyTransaction);
        try {
            shopService.processTransactions(supplyWithoutBalanceInfo);
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Missing balance info about of fruit "
                    + "for supply operation, fruit: " + supplyTransaction.getFruit().getName()));
        }
    }

    @Test
    public void processTransactions_correctInputValue_ok() {
        List<FruitTransaction> correctTransactions = new ArrayList<>();
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(FRUIT, 0);
        correctTransactions.add(balanceTransaction);
        correctTransactions.add(returnTransaction);
        correctTransactions.add(supplyTransaction);
        correctTransactions.add(purchaseTransaction);
        Assert.assertTrue(shopService.processTransactions(correctTransactions));
        Map<Fruit, Integer> actual = Storage.fruitStorage;
        Assert.assertEquals(expected, actual);
    }
}
