package core.basesyntax.serviceimpl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvToFruitTransactionConverterTest {
    private final CsvToFruitTransactionConverter parserService
            = new CsvToFruitTransactionConverter();

    @BeforeAll
    static void beforeAll() {
    }

    @Test
    void parse_ok() {
        List<String> inputData = List.of("b,banana,20",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10");
        List<FruitTransaction> expectedFruitTransactions = List.of(
                new FruitTransaction(BALANCE, "banana", 20),
                new FruitTransaction(SUPPLY, "banana", 100),
                new FruitTransaction(PURCHASE, "banana", 13),
                new FruitTransaction(RETURN, "apple", 10));
        List<FruitTransaction> actualTransactions = parserService.convertToTransaction(inputData);
        Assertions.assertEquals(expectedFruitTransactions, actualTransactions);
    }

    @Test
    void parse_nullData_notOk() {
        assertThrows(NullPointerException.class,
                () -> parserService.convertToTransaction(null));
    }
}
