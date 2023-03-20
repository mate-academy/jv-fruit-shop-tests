package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static final FruitTransaction.Operation BALANCE = FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation SUPPLY = FruitTransaction.Operation.SUPPLY;
    private static final FruitTransaction.Operation RETURN = FruitTransaction.Operation.RETURN;
    private static final FruitTransaction.Operation PURCHASE = FruitTransaction.Operation.PURCHASE;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static List<String> stringList;
    private static List<FruitTransaction> fruitTransactions;
    private static ParserServiceImpl dataParserService;

    @BeforeClass
    public static void beforeClass() {
        stringList = new ArrayList<>();
        fruitTransactions = new ArrayList<>();
        dataParserService = new ParserServiceImpl();

        stringList.add("b,banana,20");
        stringList.add("b,apple,100");
        stringList.add("s,banana,100");
        stringList.add("p,banana,13");
        stringList.add("r,apple,10");
        stringList.add("p,apple,20");
        stringList.add("p,banana,5");
        stringList.add("s,banana,50");

        fruitTransactions.add(new FruitTransaction(BALANCE,BANANA,20));
        fruitTransactions.add(new FruitTransaction(BALANCE,APPLE,100));
        fruitTransactions.add(new FruitTransaction(SUPPLY,BANANA,100));
        fruitTransactions.add(new FruitTransaction(PURCHASE,BANANA,13));
        fruitTransactions.add(new FruitTransaction(RETURN,APPLE,10));
        fruitTransactions.add(new FruitTransaction(PURCHASE,APPLE,20));
        fruitTransactions.add(new FruitTransaction(PURCHASE,BANANA,5));
        fruitTransactions.add(new FruitTransaction(SUPPLY,BANANA,50));

    }

    @Test
    public void parseTransaction_ok() {
        List<FruitTransaction> actualTransactions
                = dataParserService.parseFruitTransactions(stringList);

        assertEquals(fruitTransactions.size(),actualTransactions.size());
        FruitTransaction actual;
        FruitTransaction expected;
        for (int i = 0; i < fruitTransactions.size(); i++) {
            actual = actualTransactions.get(i);
            expected = fruitTransactions.get(i);
            assertEquals(expected.getFruit(), actual.getFruit());
            assertEquals(expected.getOperation(),actual.getOperation());
            assertEquals(expected.getQuantity(),actual.getQuantity());
        }
    }
}
