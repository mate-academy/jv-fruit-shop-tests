package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitTransactionCsvParser;
import core.basesyntax.dao.FruitTransactionCsvParserImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.Test;

public class FruitTransactionCsvParserTest {
    private static final String FRUIT_TRANSACTION_FILE_NAME = "src/test/resources/transactions.csv";

    @Test
    public void fruitTransactionCsvParser_CorrectPath_Ok() {
        FruitTransactionCsvParser fruitTransactionDao = new FruitTransactionCsvParserImpl();
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50)
        );
        List<FruitTransaction> actual = fruitTransactionDao
                .parse(FRUIT_TRANSACTION_FILE_NAME);
        assertEquals(expected, actual);
    }
}
