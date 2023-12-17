package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.strategy.ShopActivities;
import core.basesyntax.strategy.ShopBalance;
import core.basesyntax.strategy.ShopPurchase;
import core.basesyntax.strategy.ShopReturn;
import core.basesyntax.strategy.ShopSupply;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopActivitiesTest {
    private static ShopActivities balanceActivity;
    private static ShopActivities supplyActivity;
    private static ShopActivities purchaseActivity;
    private static ShopActivities returnActivity;
    private static final String FRUIT_TYPE = "apple";
    private static final String NEW_FRUIT_TYPE = "banana";
    private static final int FRUIT_AMOUNT = 150;
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        balanceActivity = new ShopBalance();
        supplyActivity = new ShopSupply();
        purchaseActivity = new ShopPurchase();
        returnActivity = new ShopReturn();
        transaction = new FruitTransaction();
    }

    @BeforeEach
    void setUp() {
        transaction.setFruitType(FRUIT_TYPE);
        transaction.setFruitAmount(FRUIT_AMOUNT);
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruits.clear();
    }

    @Test
    void updateStorageData_transactionWithNegativeValue_notOk() {
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruitAmount(-FRUIT_AMOUNT);
        assertThrows(RuntimeException.class, () -> balanceActivity.updateStorageData(transaction));
    }

    @Test
    void updateStorageData_transactionWithZeroValue_notOk() {
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruitAmount(0);
        assertThrows(RuntimeException.class, () -> balanceActivity.updateStorageData(transaction));
    }

    @Test
    void updateStorageData_transactionBalance_ok() {
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceActivity.updateStorageData(transaction);
        int expected = FRUIT_AMOUNT;
        int actual = FruitStorage.fruits.get(FRUIT_TYPE);
        assertEquals(expected, actual);
    }

    @Test
    void updateStorageData_transactionBalanceTwoTimes_notOk() {
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceActivity.updateStorageData(transaction);
        assertThrows(RuntimeException.class, () -> balanceActivity.updateStorageData(transaction));
    }

    @Test
    void updateStorageData_transactionPurchaseIncorrectFruitType_notOk() {
        FruitStorage.fruits.put(FRUIT_TYPE, FRUIT_AMOUNT);

        transaction.setFruitAmount(FRUIT_AMOUNT);
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruitType(NEW_FRUIT_TYPE);

        assertThrows(RuntimeException.class, () -> purchaseActivity.updateStorageData(transaction));
    }

    @Test
    void updateStorageData_transactionPurchase_Ok() {
        int soldFruits = 100;
        FruitStorage.fruits.put(FRUIT_TYPE, FRUIT_AMOUNT);

        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruitAmount(soldFruits);
        purchaseActivity.updateStorageData(transaction);

        int expected = FRUIT_AMOUNT - soldFruits;
        int actual = FruitStorage.fruits.get(FRUIT_TYPE);
        assertEquals(expected, actual);
    }

    @Test
    void updateStorageData_transactionPurchaseMoreThanStored_Ok() {
        int tryToSell = 200;
        FruitStorage.fruits.put(FRUIT_TYPE, FRUIT_AMOUNT);

        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruitAmount(tryToSell);
        purchaseActivity.updateStorageData(transaction);

        int expected = 0;
        int actual = FruitStorage.fruits.get(FRUIT_TYPE);
        assertEquals(expected, actual);
    }

    @Test
    void updateStorageData_transactionReturn_ok() {
        int returnFruit = 200;
        FruitStorage.fruits.put(FRUIT_TYPE, FRUIT_AMOUNT);

        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruitAmount(returnFruit);
        returnActivity.updateStorageData(transaction);

        int expected = FRUIT_AMOUNT + returnFruit;
        int actual = FruitStorage.fruits.get(FRUIT_TYPE);
        assertEquals(expected, actual);
    }

    @Test
    void updateStorageData_transactionReturnNonExistingFruitType_notOk() {
        int returnFruit = 200;
        FruitStorage.fruits.put(FRUIT_TYPE, FRUIT_AMOUNT);

        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruitAmount(returnFruit);
        transaction.setFruitType(NEW_FRUIT_TYPE);

        assertThrows(RuntimeException.class, () -> returnActivity.updateStorageData(transaction));
    }

    @Test
    void updateStorageData_transactionSupply_ok() {
        int supplyFruit = 250;
        FruitStorage.fruits.put(FRUIT_TYPE, FRUIT_AMOUNT);

        transaction.setFruitAmount(supplyFruit);
        supplyActivity.updateStorageData(transaction);
        int expected = FRUIT_AMOUNT + supplyFruit;
        int actual = FruitStorage.fruits.get(FRUIT_TYPE);
        assertEquals(expected, actual);

        transaction.setFruitType(NEW_FRUIT_TYPE);
        transaction.setFruitAmount(supplyFruit);
        supplyActivity.updateStorageData(transaction);
        expected = supplyFruit;
        actual = FruitStorage.fruits.get(NEW_FRUIT_TYPE);
        assertEquals(expected, actual);
    }
}
