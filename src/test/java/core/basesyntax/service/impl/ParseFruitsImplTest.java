package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseFruits;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParseFruitsImplTest {
    private ParseFruits parseFruits;
    private List<String> list;
    private List<FruitTransaction> actual;

    @Before
    public void setUp() {
        parseFruits = new ParseFruitsImpl();
        list = new ArrayList<>(List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20",
                "p,banana,5", "s,banana,50"));
        actual = new ArrayList<>();
        actual.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana",20));
        actual.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        actual.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        actual.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        actual.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        actual.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
        actual.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));
        actual.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
    }

    @Test
    public void parseFruits_ValidList_Ok() {
        List<FruitTransaction> expected = parseFruits.parse(list);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void parseFruits_NullList_NotOk() {
        parseFruits.parse(null);
    }
}
