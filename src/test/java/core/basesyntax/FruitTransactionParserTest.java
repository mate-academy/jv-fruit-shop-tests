package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import java.util.List;
import org.junit.Test;

public class FruitTransactionParserTest {
    private static final FruitTransactionParser FRUIT_TRANSACTION_PARSER =
            new FruitTransactionParserImpl();

    @Test
    public void toTransaction_validCase_ok() {
        String data = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,200\n" + System.lineSeparator()
                + "b,apple,100\n" + System.lineSeparator()
                + "s,banana,100\n" + System.lineSeparator()
                + "p,banana,13\n";
        List<FruitTransaction> actual = FRUIT_TRANSACTION_PARSER.toTransaction(data);
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
        FRUIT_TRANSACTION_PARSER.toTransaction(null);
    }
}
