package core.service.impl;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.Parser;
import core.basesyntax.service.impl.ParseImpl;
import org.junit.Test;

public class ParserImplTest {

    public void expectedList(List<FruitTransaction> fruitTransactionsList) {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.getByCode("b"),
                "banana", 5
        );
        fruitTransactionsList.add(fruitTransaction);
        FruitTransaction fruitTransaction2 = new FruitTransaction(
                FruitTransaction.Operation.getByCode("s"),
                "banana", 25
        );
        fruitTransactionsList.add(fruitTransaction2);
        FruitTransaction fruitTransaction3 = new FruitTransaction(
                FruitTransaction.Operation.getByCode("p"),
                "banana", 10
        );
        fruitTransactionsList.add(fruitTransaction3);
        FruitTransaction fruitTransaction4 = new FruitTransaction(
                FruitTransaction.Operation.getByCode("r"),
                "banana", 5
        );
        fruitTransactionsList.add(fruitTransaction4);
    }

    private List<String> listForParser() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,5");
        lines.add("s,banana,25");
        lines.add("p,banana,10");
        lines.add("r,banana,5");
        return lines;
    }

    @Test
    public void parse_Ok() {
        List<FruitTransaction> expectedList = new ArrayList<>();
        expectedList(expectedList);
        Parser parser = new ParseImpl();
        List<FruitTransaction> actualList = parser.parse(listForParser());
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test(expected = NullPointerException.class)
    public void parse_toNull_input_notOk() {
        Parser parser = new ParseImpl();
        parser.parse(null);
    }

    @Test
    public void parse_empty_Ok() {
        List<FruitTransaction> expectedList = new ArrayList<>();
        Parser parser = new ParseImpl();
        List<FruitTransaction> actualList = parser.parse(new ArrayList<>());
        assertEquals(expectedList.size(), actualList.size());
    }
}
