package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ParseServiceImplTest {
    private ParseService parseService = new ParseServiceImpl();
    private List<String> dataFromFile = new ArrayList<>();
    private List<FruitTransaction> expectedData = new ArrayList<>();

    @Test
    public void parseTransaction_correctData_ok() {
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("b,banana,20");
        dataFromFile.add("b,apple,100");
        dataFromFile.add("s,banana,100");
        dataFromFile.add("p,banana,13");
        dataFromFile.add("r,apple,10");
        dataFromFile.add("p,apple,20");
        dataFromFile.add("p,banana,5");
        dataFromFile.add("s,banana,50");
        expectedData.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expectedData.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expectedData.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expectedData.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expectedData.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        expectedData.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
        expectedData.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));
        expectedData.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
        List<FruitTransaction> actualData = parseService.parseTransaction(dataFromFile);
        assertEquals(expectedData, actualData);
    }

    @Test(expected = RuntimeException.class)
    public void parseTransaction_nullData_notOk() {
        parseService.parseTransaction(null);
    }
}
