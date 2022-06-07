package core.basesyntax.services;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceServiceImplTest {
    private static BalanceService balanceService;
    private static List<FruitTransaction> fruitTransactionList;

    @BeforeClass
    public static void beforeAll() {
        balanceService = new BalanceServiceImpl();
        Storage.fruitsInfo.put("banana", 20);
        Storage.fruitsInfo.put("apple", 20);
        Storage.fruitsInfo.put("orange", 20);
        fruitTransactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana",20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple",20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange",20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana",50),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange",10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple",20));
    }

    @Test
    public void balance_OK() {
        Map<String, Integer> testMapOfFruit = new HashMap<>();
        testMapOfFruit.put("banana",70);
        testMapOfFruit.put("apple", 0);
        testMapOfFruit.put("orange", 30);
        balanceService.balance(fruitTransactionList);
        Assert.assertEquals(Storage.fruitsInfo, testMapOfFruit);
    }
}
