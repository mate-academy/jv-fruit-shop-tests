package core.basesyntax.services;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService fruitService;
    private static List<FruitTransaction> fruitTransactionList;
    private static List<String> listOfTransactions;
    
    @BeforeClass
    public static void beforeAll() {
        fruitService = new FruitServiceImpl();
        fruitTransactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana",20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple",20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange",20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana",50),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange",10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple",20));
        listOfTransactions = List.of("b,banana,20",
                "b,apple,20",
                "b,orange,20",
                "s,banana,50",
                "r,orange,10",
                "p,apple,20");
    }

    @Test
    public void addUniqueFruitsToStorage_Ok() {
        Map<String, Integer> actual = new HashMap<>();
        actual.put("banana", 20);
        actual.put("apple", 20);
        actual.put("orange", 20);
        fruitService.addUniqueFruitsToStorage(fruitTransactionList);
        Assert.assertEquals(actual, Storage.fruitsInfo);
        Storage.fruitsInfo.clear();
    }

    @Test
    public void getListOfTransactions_Ok() {
        Assert.assertEquals(fruitTransactionList,
                fruitService.getListOfTransactions(listOfTransactions));
    }
}
