package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataParserImplTest {
    private DataParser dataParser;
    private List<String> transactions;

    @Before
    public void setUp() {
        dataParser = new DataParserImpl();
        transactions = List.of("b,apple,100", "b,orange,125",
                "s,apple,50", "r,orange,5", "p,orange,30", "p,apple,55");
    }

    @Test
    public void parseData_Ok() {
        List<FruitTransaction> expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 125));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 5));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 30));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 55));
        List<FruitTransaction> actualList = dataParser.parse(transactions);
        int actualCountEquals = 0;
        for (int i = 0; i < actualList.size(); i++) {
            if (actualList.get(i).equals(expectedList.get(i))) {
                actualCountEquals++;
            }
        }
        int expectedCountResult = transactions.size();
        Assert.assertEquals(expectedCountResult, actualCountEquals);
    }

    @Test (expected = RuntimeException.class)
    public void parseNullList_NotOk() {
        dataParser.parse(null);
    }

    @Test (expected = RuntimeException.class)
    public void parseWrongDataList_NotOk() {
        List<String> wrongDataList = List.of("g,grape,300", "t,apple,200");
        dataParser.parse(wrongDataList);
    }
}
