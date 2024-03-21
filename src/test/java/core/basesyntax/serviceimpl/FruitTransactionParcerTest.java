package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitOperation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitTransactionParcerTest {
    private static final FruitOperation BALANCE = FruitOperation.BALANCE;
    private static final FruitOperation SUPPLY = FruitOperation.SUPPLY;
    private static final FruitOperation RETURN = FruitOperation.RETURN;
    private static final FruitOperation PURCHASE = FruitOperation.PURCHASE;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static List<String> stringList;
    private static List<FruitTransaction> fruitTransactions = new ArrayList<>();
    private static FruitTransactionParser dataParserService = new FruitTransactionParserImpl();

    @BeforeAll
    public static void beforeAll() {
        stringList = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");

        fruitTransactions.add(new FruitTransaction(BALANCE, BANANA, 20));
        fruitTransactions.add(new FruitTransaction(BALANCE, APPLE, 100));
        fruitTransactions.add(new FruitTransaction(SUPPLY, BANANA, 100));
        fruitTransactions.add(new FruitTransaction(PURCHASE, BANANA, 13));
        fruitTransactions.add(new FruitTransaction(RETURN, APPLE, 10));
        fruitTransactions.add(new FruitTransaction(PURCHASE, APPLE, 20));
        fruitTransactions.add(new FruitTransaction(PURCHASE, BANANA, 5));
        fruitTransactions.add(new FruitTransaction(SUPPLY, BANANA, 50));
    }

    @Test
    public void parse_emptyInput_notOk() {
        List<FruitTransaction> fruitTransactions
                = dataParserService.parseFruitTransactions(new ArrayList<>());
        assertEquals(0, fruitTransactions.size());
    }

    @Test
    public void parse_correctTransaction_Ok() {
        List<FruitTransaction> actualTransactions
                = dataParserService.parseFruitTransactions(stringList);
        assertEquals(fruitTransactions.size(), actualTransactions.size());

        List<FruitTransaction> actualFruitTransaction
                = dataParserService.parseFruitTransactions(stringList);
        assertEquals(fruitTransactions, actualFruitTransaction);
    }
}
