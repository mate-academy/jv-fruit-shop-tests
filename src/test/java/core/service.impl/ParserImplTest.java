package core.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.Parser;
import core.basesyntax.service.impl.ParseImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ParserImplTest {
    private static final Parser parser = new ParseImpl();

    private void getExpectedList(List<FruitTransaction> fruitTransactionsList) {
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

    private List<String> getTestDataForParser() {
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
        getExpectedList(expectedList);
        List<FruitTransaction> actualList = parser.parse(getTestDataForParser());
        assertEquals(expectedList.size(), actualList.size());
    }

    @Test
    public void parse_empty_Ok() {
        List<FruitTransaction> actualList = parser.parse(new ArrayList<>());
        assertTrue(actualList.isEmpty());
    }
}
