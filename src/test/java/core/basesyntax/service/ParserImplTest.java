package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParserImplTest {
    private static Parser parser;
    private static List<String> lines;

    @Before
    public void setUp() throws Exception {
        lines = new ArrayList<>();
        parser = new ParserImpl();
    }

    @Test
    public void parse_Ok() {
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,100");
        lines.add("s,banana,100");
        lines.add("p,banana,13");
        lines.add("r,apple,10");
        lines.add("p,apple,20");
        lines.add("p,banana,5");
        lines.add("s,banana,50");
        List<FruitTransaction> expectedList = new ArrayList<>();
        FruitTransaction fruitTransaction1 = new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("b"), 20);
        expectedList.add(fruitTransaction1);
        FruitTransaction fruitTransaction2 = new FruitTransaction("apple",
                FruitTransaction.Operation.getByCode("b"), 100);
        expectedList.add(fruitTransaction2);
        FruitTransaction fruitTransaction3 = new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("s"), 100);
        expectedList.add(fruitTransaction3);
        FruitTransaction fruitTransaction4 = new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("p"), 13);
        expectedList.add(fruitTransaction4);
        FruitTransaction fruitTransaction5 = new FruitTransaction("apple",
                FruitTransaction.Operation.getByCode("r"), 10);
        expectedList.add(fruitTransaction5);
        FruitTransaction fruitTransaction6 = new FruitTransaction("apple",
                FruitTransaction.Operation.getByCode("p"), 20);
        expectedList.add(fruitTransaction6);
        FruitTransaction fruitTransaction7 = new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("p"), 5);
        expectedList.add(fruitTransaction7);
        FruitTransaction fruitTransaction8 = new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("s"), 50);
        expectedList.add(fruitTransaction8);
        List<FruitTransaction> actualList = parser.parse(lines);
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test(expected = NullPointerException.class)
    public void parse_nullInput_notOk() {
        parser.parse(null);
    }

    @Test
    public void parse_emptyInputList_Ok() {
        List<FruitTransaction> actualList = parser.parse(lines);
        List<FruitTransaction> expectedList = new ArrayList<>();
        assertEquals(expectedList.size(), actualList.size());
    }
}
