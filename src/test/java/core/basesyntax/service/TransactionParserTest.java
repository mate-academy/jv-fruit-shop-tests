package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.StoreOperation;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TransactionParserTest {
    private TransactionParser transactionParser = new TransactionParser();
    private List<String> dataList = new ArrayList<>();

    @Test
    public void parseValidTransaction_Ok() {
        dataList.add("type,fruit,quantity");
        dataList.add("b,banana,20");
        dataList.add("s,banana,100");
        dataList.add("p,banana,13");
        dataList.add("r,banana,10");
        List<FruitTransaction> expectedResult = new ArrayList<>();
        expectedResult.add(new FruitTransaction(StoreOperation.BALANCE, "banana", 20));
        expectedResult.add(new FruitTransaction(StoreOperation.SUPPLY, "banana", 100));
        expectedResult.add(new FruitTransaction(StoreOperation.PURCHASE, "banana", 13));
        expectedResult.add(new FruitTransaction(StoreOperation.RETURN, "banana", 10));
        List<FruitTransaction> actualResult = transactionParser.parse(dataList);
        assertEquals(expectedResult.get(2).getStoreOperation(),
                actualResult.get(2).getStoreOperation());
    }

    @Test(expected = RuntimeException.class)
    public void parseInvalidStoreOperationTransaction_NotOk() {
        dataList.add("type,fruit,quantity");
        dataList.add("q,banana,20");
        transactionParser.parse(dataList);
    }
}
