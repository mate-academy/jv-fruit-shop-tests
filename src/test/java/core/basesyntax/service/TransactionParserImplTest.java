package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private static List<String> correctLines;
    private static List<String> incorrectLines;
    private static List<FruitTransaction> transactions;
    private static TransactionParser transactionParser;

    @Before
    public void setUp() {
        correctLines = List.of("type,fruit,quantity",
                "b,banana,20");
        incorrectLines = List.of("type,fruit,quantity",
                ",banana,20");
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        transactions = List.of(fruitTransaction);
        transactionParser = new TransactionParserImpl();
    }

    @Test
    public void parseStringToTransaction_Ok() {
        List<FruitTransaction> actual = transactionParser.parseFruitTransaction(correctLines);
        List<FruitTransaction> expected = transactions;
        Assert.assertEquals(expected.get(0).getOperation(), actual.get(0).getOperation());
    }

    @Test
    public void parseIncorrectLines() {
        List<FruitTransaction> actual = transactionParser.parseFruitTransaction(incorrectLines);
        Assert.assertNull(actual.get(0).getOperation());
    }
}
