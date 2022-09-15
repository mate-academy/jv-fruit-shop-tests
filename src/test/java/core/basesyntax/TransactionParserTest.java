package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.TransactionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserTest {
    private static final String SUPPLY = "s";
    private static final String APPLE = "apple";
    private static TransactionParser parser;
    private static List<String> dataToParse;
    private static List<Transaction> expectedParsedData;
    private static Transaction transaction;

    @BeforeClass
    public static void before() {
        parser = new TransactionParserImpl();
        expectedParsedData = new ArrayList<>();
        dataToParse = new ArrayList<>();
        dataToParse.add("type,fruit,quantity");
        dataToParse.add("s,apple,30");
        transaction = new Transaction(Transaction.Operation.getByCode(SUPPLY), APPLE, 30);
    }

    @Test
    public void getTransaction_ValidDataTransaction_Ok() {
        expectedParsedData.add(transaction);
        List<Transaction> actualParsedData;
        actualParsedData = parser.getTransactions(dataToParse);
        assertEquals(expectedParsedData.get(0).getOperation(),
                actualParsedData.get(0).getOperation());
        assertEquals(expectedParsedData.get(0).getProductName(),
                actualParsedData.get(0).getProductName());
        assertEquals(expectedParsedData.get(0).getQuantity(),
                actualParsedData.get(0).getQuantity());

    }

    @Test(expected = RuntimeException.class)
    public void getTransaction_NotEnoughElements_Not_Ok() {
        dataToParse.add("p,fruit,");
        parser.getTransactions(dataToParse);
    }

    @Test(expected = RuntimeException.class)
    public void getTransaction_NotNumber_Not_Ok() {
        dataToParse.add("p,fruit,five");
        parser.getTransactions(dataToParse);
    }
}
