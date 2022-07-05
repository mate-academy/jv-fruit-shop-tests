package core.basesyntax.service.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionConvertor;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionConvertorImplTest {
    private static TransactionConvertor transactionConvertor;

    @BeforeClass
    public static void beforeClass() {
        transactionConvertor = new TransactionConvertorImpl();
    }

    @Test
    public void convert_Ok() {
        List<String> input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("b,banana,20");
        input.add("b,apple,100");
        input.add("s,banana,100");
        input.add("p,banana,13");
        input.add("r,apple,10");
        input.add("p,apple,20");
        input.add("p,banana,5");
        input.add("s,banana,50");

        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction(Transaction.Operation.getOperation("b"),
                new Fruit("banana"), Integer.valueOf("20")));
        expected.add(new Transaction(Transaction.Operation.getOperation("b"),
                new Fruit("apple"), Integer.valueOf("100")));
        expected.add(new Transaction(Transaction.Operation.getOperation("s"),
                new Fruit("banana"), Integer.valueOf("100")));
        expected.add(new Transaction(Transaction.Operation.getOperation("p"),
                new Fruit("banana"), Integer.valueOf("13")));
        expected.add(new Transaction(Transaction.Operation.getOperation("r"),
                new Fruit("apple"), Integer.valueOf("10")));
        expected.add(new Transaction(Transaction.Operation.getOperation("p"),
                new Fruit("apple"), Integer.valueOf("20")));
        expected.add(new Transaction(Transaction.Operation.getOperation("p"),
                new Fruit("banana"), Integer.valueOf("5")));
        expected.add(new Transaction(Transaction.Operation.getOperation("s"),
                new Fruit("banana"), Integer.valueOf("50")));

        List<Transaction> actual = transactionConvertor.convert(input);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void convert_emptyString_Ok() {
        List<Transaction> expected = new ArrayList<>();
        List<String> input = new ArrayList<>();
        input.clear();
        expected.clear();
        List<Transaction> actual = transactionConvertor.convert(input);
        assertEquals(expected, actual);
    }
}
