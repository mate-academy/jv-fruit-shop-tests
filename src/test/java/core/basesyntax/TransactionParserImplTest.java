package core.basesyntax;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.servises.TransactionParser;
import core.basesyntax.servises.impl.TransactionParserImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static final String LIST_OF_FRUIT_TRANSACTION = "[FruitTransaction{operation=BALANCE,"
            + " fruit='banana', amount=20},"
            + " FruitTransaction{operation=BALANCE, fruit='apple', amount=100},"
            + " FruitTransaction{operation=SUPPLY, fruit='banana', amount=100},"
            + " FruitTransaction{operation=PURCHASE, fruit='banana', amount=13}]";
    private static final List<String> test = Arrays.asList(
            "b,banana,20", "b,apple,100", "s,banana,100","p,banana,13");
    private static TransactionParser transactionParser;

    @BeforeClass
    public static void setUp() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    public void transactionParser_correctTest_ok() {
        List<FruitTransaction> fruitTransactions =
                transactionParser.interfaceTransactionParser(test);
        Assert.assertEquals(LIST_OF_FRUIT_TRANSACTION, fruitTransactions.toString());
    }
}
