package core.basesyntax.sarvice.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitTransactionParserServiceImpl;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserServiceImplTest {
    private static FruitTransactionParserServiceImpl parser;
    private List<FruitTransaction> expected;

    {
        expected = new LinkedList<>();
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 100));
    }

    @BeforeClass
    public static void beforeClass() {
        parser = new FruitTransactionParserServiceImpl();
    }

    @Test
    public void parse_notEmptyList_Ok() {
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.addAll(List.of("b,banana,20", "b,apple,100", "s,banana,100"));
        List<FruitTransaction> actual = parser.parse(inputData);
        Assert.assertEquals(expected, actual);
    }
}
