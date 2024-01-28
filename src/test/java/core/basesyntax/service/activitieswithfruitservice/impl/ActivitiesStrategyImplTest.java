package core.basesyntax.service.activitieswithfruitservice.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.activitieswithfruitservice.ActivitiesStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ActivitiesStrategyImplTest {
    private final ActivitiesStrategy activitiesStrategy = new ActivitiesStrategyImpl();

    @Test
    void testCodeBalanceInActivitiesStrategy_ok() {
        int expectedQuantityOrangeInStorage = 100;
        int moreThanShouldBeInStorageOrange = 110;
        String codeBalance = "b";
        FruitTransaction fruitTransactionBalanceOrange
                = new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 100);
        activitiesStrategy.performTransaction(codeBalance, fruitTransactionBalanceOrange);
        Assertions.assertEquals(expectedQuantityOrangeInStorage,
                Storage.fruitStorage.get("orange"));
        Assertions.assertNotEquals(moreThanShouldBeInStorageOrange,
                Storage.fruitStorage.get("orange"));
    }

    @Test
    void testCodePurchaseInActivitiesStrategy_ok() {
        Storage.fruitStorage.put("orange", 100);
        int expectedQuantityOrangeInStorage = 80;
        int moreThanShouldBeInStorageOrange = 100;
        String codeBalance = "p";
        FruitTransaction fruitTransactionPurchaseOrange
                = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 20);
        activitiesStrategy.performTransaction(codeBalance, fruitTransactionPurchaseOrange);
        Assertions.assertEquals(expectedQuantityOrangeInStorage,
                Storage.fruitStorage.get("orange"));
        Assertions.assertNotEquals(moreThanShouldBeInStorageOrange,
                Storage.fruitStorage.get("orange"));
    }

    @Test
    void testCodeReturnInActivitiesStrategy_ok() {
        Storage.fruitStorage.put("orange", 80);
        int expectedQuantityOrangeInStorage = 90;
        int moreThanShouldBeInStorageOrange = 100;
        String codeBalance = "r";
        FruitTransaction fruitTransactionReturnOrange
                = new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 10);
        activitiesStrategy.performTransaction(codeBalance, fruitTransactionReturnOrange);
        Assertions.assertEquals(expectedQuantityOrangeInStorage,
                Storage.fruitStorage.get("orange"));
        Assertions.assertNotEquals(moreThanShouldBeInStorageOrange,
                Storage.fruitStorage.get("orange"));
    }

    @Test
    void testCodeSupplyInActivitiesStrategy_ok() {
        Storage.fruitStorage.put("orange", 100);
        int expectedQuantityOrangeInStorage = 150;
        int moreThanShouldBeInStorageOrange = 160;
        String codeBalance = "s";
        FruitTransaction fruitTransactionSupplyOrange
                = new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 50);
        activitiesStrategy.performTransaction(codeBalance, fruitTransactionSupplyOrange);
        Assertions.assertEquals(expectedQuantityOrangeInStorage,
                Storage.fruitStorage.get("orange"));
        Assertions.assertNotEquals(moreThanShouldBeInStorageOrange,
                Storage.fruitStorage.get("orange"));
    }

    @Test
    void testInvalidInActivitiesStrategy_ok() {
        String codeBalance = "w";
        FruitTransaction fruitTransactionSupplyOrange
                = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 50);
        Assertions.assertThrows(RuntimeException.class,
                () -> activitiesStrategy
                        .performTransaction(codeBalance, fruitTransactionSupplyOrange));
    }
}
