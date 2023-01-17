package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserTest {
    private static FruitTransactionParser fruitTransactionParser;

    @BeforeClass
    public static void setUp() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test
    public void toTransaction_validCase_ok() {
        String data = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,200" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13";
        List<FruitTransaction> actual = fruitTransactionParser.toTransaction(data);
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 200),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13)
        );
        assertEquals("Invalid transaction", expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void toTransaction_nullValue_notOk() {
        fruitTransactionParser.toTransaction(null);
    }
}
