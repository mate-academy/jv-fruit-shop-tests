package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class TransactionParserServiceTest {
    @Test
    public void transactionParser_parse_ok() {
        List<String> read = new ArrayList<>();
        read.add("action,fruit,amount");
        read.add("b,banana,10");
        read.add("b,apple,20");
        read.add("p,banana,5");
        read.add("s,apple,5");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.getOperationByCode("b"),
                "banana",
                10));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getOperationByCode("b"),
                "apple",
                20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getOperationByCode("p"),
                "banana",
                5));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getOperationByCode("s"),
                "apple",
                5));
        List<FruitTransaction> actual = new TransactionParserServiceImpl().parse(read);
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test(expected = NullPointerException.class)
    public void transactionParser_parse_emptyInput_exception() {
        List<String> read = new ArrayList<>();
        new TransactionParserServiceImpl().parse(read);
    }
}
