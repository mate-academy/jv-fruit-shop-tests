package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ParserImplTest {

    @Test
    public void parse_Ok() {
        List<FruitTransaction> expectedList = new ArrayList<>();
        fillExpectedList(expectedList);
        Parser parser = new ParserImpl();
        List<FruitTransaction> actualList = parser.parse(prepareListForParser());
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test(expected = NullPointerException.class)
    public void parse_nullInput_notOk() {
        Parser parser = new ParserImpl();
        parser.parse(null);
    }

    @Test
    public void parse_emptyInputList_Ok() {
        List<FruitTransaction> expectedList = new ArrayList<>();
        Parser parser = new ParserImpl();
        List<FruitTransaction> actualList = parser.parse(new ArrayList<>());
        assertEquals(expectedList.size(), actualList.size());
    }

    private void fillExpectedList(List<FruitTransaction> expectedList) {
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
    }

    private List<String> prepareListForParser() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,100");
        lines.add("s,banana,100");
        lines.add("p,banana,13");
        lines.add("r,apple,10");
        lines.add("p,apple,20");
        lines.add("p,banana,5");
        lines.add("s,banana,50");
        return lines;
    }
}
