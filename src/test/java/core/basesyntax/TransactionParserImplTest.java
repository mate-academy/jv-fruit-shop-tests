package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.TransactionParserImpl;
import org.junit.Assert;
import org.junit.Test;

public class TransactionParserImplTest {
    private static final Fruit testFruit = new Fruit("banana");
    private static final FruitTransaction fruitTransaction
            = new FruitTransaction("s", testFruit, 3);
    private static final TransactionParser transactionParser = new TransactionParserImpl();

    @Test
    public void validInput_Ok() {
        FruitTransaction actual = transactionParser.parse("s,banana,3");
        Assert.assertTrue(fruitTransaction.getTransactionName()
                .equals(actual.getTransactionName())
                && fruitTransaction.getFruit()
                .equals(actual.getFruit()) && fruitTransaction.getQuantity()
                == actual.getQuantity());

    }
}
