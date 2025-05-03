package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseTransactionImplTest {
    private static final String HEADER_REPORT = "type,fruit,quantity";
    private static List<String> dataList = new ArrayList<>();
    private static List<String> emptyList = new ArrayList<>();
    private static ParseTransaction parseTransaction;

    @BeforeClass
    public static void setUp() {
        emptyList.add(HEADER_REPORT);
        dataList.add(HEADER_REPORT);
        parseTransaction = new ParseTransactionImpl();
    }

    @Test
    public void test_parse_empty_ok() {
        int expected = 0;
        List<FruitTransaction> result = parseTransaction.processing(emptyList);
        Assert.assertEquals("Must be equals", expected, result.size());
    }

    @Test
    public void test_parse_data_ok() {
        boolean actual = true;
        String separator = ",";
        dataList.add("b,banana,20");
        dataList.add("b,apple,100");
        dataList.add("s,banana,100");
        dataList.add("p,banana,13");
        dataList.add("r,apple,10");
        dataList.add("p,apple,20");
        dataList.add("p,banana,5");
        dataList.add("s,banana,50");
        List<FruitTransaction> result = parseTransaction.processing(dataList);
        String line = "";

        for (FruitTransaction fruitTransaction: result) {
            line = fruitTransaction.getType().getLabel() + separator
                    + fruitTransaction.getFruit().getName() + separator
                    + fruitTransaction.getCount();
            if (!dataList.remove(line)) {
                actual = false;
                break;
            }
        }
        if (actual) {
            if (dataList.size() != 1 || !dataList.get(0).equals(HEADER_REPORT)) {
                actual = false;
            }
        }
        Assert.assertTrue("Must be equals", actual);
    }
}
