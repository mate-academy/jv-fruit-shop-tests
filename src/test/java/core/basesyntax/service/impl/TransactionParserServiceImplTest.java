package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserServiceImplTest {
    private static TransactionParserService transactionParserService;

    @BeforeClass
    public static void beforeClass() {
        transactionParserService = new TransactionParserServiceImpl();
    }

    @Test
    public void parse_validDataSheet_Ok() {
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setQuantity(20);
        fruitTransaction1.setFruit(new Fruit("banana"));
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);

        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setQuantity(100);
        fruitTransaction2.setFruit(new Fruit("apple"));
        fruitTransaction2.setOperation(FruitTransaction.Operation.SUPPLY);

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(fruitTransaction1);
        expected.add(fruitTransaction2);

        List<String> dataSheet = Arrays.asList("type,fruit,quantity",
                "b,banana,20",
                "s,apple,100");

        List<FruitTransaction> actual = transactionParserService.parse(dataSheet);
        Assert.assertTrue("Parsed data is not correct", actual.equals(expected));
    }

    @Test (expected = RuntimeException.class)
    public void parse_invalidDataSheet_NotOk() {
        List<String> dataSheet = Arrays.asList("type,fruit,quantity",
                "type,fruit,quantity",
                "type,fruit,quantity");
        transactionParserService.parse(dataSheet);
    }

    @Test
    public void parse_emptyDataSheet_NotOk() {
        List<String> dataSheet = List.of("");
        List<FruitTransaction> actual = transactionParserService.parse(dataSheet);
        Assert.assertTrue(actual.isEmpty());
    }
}
