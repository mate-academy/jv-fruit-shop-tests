package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.exception.InvalidQuantityException;
import core.basesyntax.service.exception.PurchasingException;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.strategy.BalanceStrategy;
import core.basesyntax.strategy.FruitShopStrategy;
import core.basesyntax.strategy.PurchaseStrategy;
import core.basesyntax.strategy.ReturnStrategy;
import core.basesyntax.strategy.SupplyStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceTest {
    private static final String FIRST_FRUIT = "fruit1";
    private static final String SECOND_FRUIT = "fruit2";
    private static FruitService fruitService;
    private final Storage storage = Storage.getInstance();
    private List<FruitTransaction> testTransactions;

    @BeforeClass
    public static void beforeClass() {
        Map<Operation, FruitShopStrategy> fruitShopStrategyMap = Map.of(
                Operation.BALANCE, new BalanceStrategy(),
                Operation.SUPPLY, new SupplyStrategy(),
                Operation.PURCHASE, new PurchaseStrategy(),
                Operation.RETURN, new ReturnStrategy()
        );
        fruitService = new FruitServiceImpl(fruitShopStrategyMap);
    }

    @Before
    public void setUp() {
        testTransactions = new ArrayList<>();
        testTransactions.add(new FruitTransaction(Operation.BALANCE, FIRST_FRUIT, 200));
    }

    @After
    public void tearDown() {
        storage.getContent().clear();
    }

    @Test
    public void process_listOfCorrectTransactions_ok() {
        testTransactions.add(new FruitTransaction(Operation.BALANCE, SECOND_FRUIT, 300));
        testTransactions.add(new FruitTransaction(Operation.PURCHASE, FIRST_FRUIT, 100));
        testTransactions.add(new FruitTransaction(Operation.SUPPLY, FIRST_FRUIT, 20));
        testTransactions.add(new FruitTransaction(Operation.RETURN, FIRST_FRUIT, 30));
        fruitService.process(testTransactions);
        Map<String, Integer> expectedStorage = Map.of(
                FIRST_FRUIT, 150,
                SECOND_FRUIT, 300
        );
        Map<String, Integer> actualStorage = storage.getContent();
        assertEquals("Test failed! Expected storage state after "
                        + testTransactions + " transactions: " + expectedStorage
                        + ", but was: " + actualStorage,
                expectedStorage, actualStorage);
    }

    @Test(expected = InvalidQuantityException.class)
    public void process_listOfTransactionsBalanceNegative_notOk() {
        testTransactions.add(new FruitTransaction(Operation.BALANCE, SECOND_FRUIT, -100));
        fruitService.process(testTransactions);
    }

    @Test(expected = InvalidQuantityException.class)
    public void process_listOfTransactionsSupplyNegative_notOk() {
        testTransactions.add(new FruitTransaction(Operation.SUPPLY, FIRST_FRUIT, -100));
        fruitService.process(testTransactions);
    }

    @Test(expected = InvalidQuantityException.class)
    public void process_listOfTransactionsPurchaseNegative_notOk() {
        testTransactions.add(new FruitTransaction(Operation.PURCHASE, FIRST_FRUIT, -100));
        fruitService.process(testTransactions);
    }

    @Test(expected = InvalidQuantityException.class)
    public void process_listOfTransactionsReturnNegative_notOk() {
        testTransactions.add(new FruitTransaction(Operation.RETURN, FIRST_FRUIT, -100));
        fruitService.process(testTransactions);
    }

    @Test(expected = PurchasingException.class)
    public void process_listOfTransactionsPurchaseMoreThanHave_notOk() {
        Storage.getInstance().getContent().put(SECOND_FRUIT, 100);
        testTransactions.add(new FruitTransaction(Operation.PURCHASE, SECOND_FRUIT, 200));
        fruitService.process(testTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void process_listOfTransactionsPurchaseNonExistenceFruit_notOk() {
        testTransactions.add(new FruitTransaction(Operation.PURCHASE, SECOND_FRUIT, 1));
        fruitService.process(testTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void process_Null_notOk() {
        fruitService.process(null);
    }

}
