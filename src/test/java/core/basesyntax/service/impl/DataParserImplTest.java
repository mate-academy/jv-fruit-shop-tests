package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class DataParserImplTest {
    private final DataParser dataParser = new DataParserImpl();

    @Test
    public void parseData_normalData_ok() {
        List<String> testData = new ArrayList<>();
        testData.add("type,fruit,quantity");
        testData.add("b,banana,20");
        testData.add("b,apple,100");
        testData.add("s,banana,100");
        testData.add("r,apple,10");
        testData.add("p,banana,5");

        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));
        List<FruitTransaction> actual = dataParser.parseData(testData);
        assertEquals(expected, actual);
    }
}
