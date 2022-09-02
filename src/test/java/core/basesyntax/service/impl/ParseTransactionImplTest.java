package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseTransactionImplTest {

    private static List<String> dataList = new ArrayList<>();
    private static List<String> emptyList = new ArrayList<>();
    private static ParseTransaction parseTransaction;

    @BeforeClass
    public static void beforeClass() {
        emptyList.add("type,fruit,quantity");
        dataList.add("type,fruit,quantity");
        dataList.add("b,banana,20");
        dataList.add("b,apple,100");
        dataList.add("s,banana,100");
        dataList.add("p,banana,13");
        dataList.add("r,apple,10");
        dataList.add("p,apple,20");
        dataList.add("p,banana,5");
        dataList.add("s,banana,50");
        parseTransaction = new ParseTransactionImpl();
    }

    @Test
    public void test_parse_empty_ok() {
        List<FruitTransaction> result = parseTransaction.processing(emptyList);
        Assert.assertEquals("Must be empty", 0, result.size());
    }

    @Test
    public void test_parse_data_ok() {
        List<FruitTransaction> result = parseTransaction.processing(dataList);
        Assert.assertEquals("Must be not empty", 8, result.size());
    }
}
