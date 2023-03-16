package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private TransactionParser transactionParser;
    private FruitTransaction fruitTransaction;
    private List<FruitTransaction> expected;
    private List<String> inputList;

    @Before
    public void setUp() {
        transactionParser = new TransactionParserImpl();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("fruit");
        fruitTransaction.setQuantity(10);
        inputList = List.of("type,fruit,quantity,", "b,fruit,10,");
        expected = new ArrayList<>();
    }

    @Test
    public void parse_ok() {
        expected.add(fruitTransaction);
        List<FruitTransaction> actual = new ArrayList<>();
        actual = transactionParser.parse(inputList);
        assertEquals(expected, actual);
    }
}
