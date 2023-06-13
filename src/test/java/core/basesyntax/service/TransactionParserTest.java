package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.CustomException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.CsvTransactionParserImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserTest {
    private static CsvTransactionParserImpl csvTransactionParser;
    private static List<String> inputStringList;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    @BeforeAll
    static void beforeAll() {
        csvTransactionParser = new CsvTransactionParserImpl();
    }

    @Test
    void parseReport_ValidReport_IsOk() {
        inputStringList = List.of("type,fruit,quantity,",
                "b,banana,20,",
                "b,apple,100,",
                "s,banana,100,",
                "p,banana,13,",
                "r,apple,10,",
                "p,apple,109,",
                "p,banana,155,",
                "s,banana,50");
        List<FruitTransaction> expected = List.of(new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, BANANA, 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE, 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 109),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 155),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 50));
        List<FruitTransaction> actual = csvTransactionParser.parseReport(inputStringList);
        assertIterableEquals(expected, actual);
    }

    @Test
    void parseReport_NullInput_NotOk() {
        assertThrows(CustomException.class, ()
                -> csvTransactionParser.parseReport(inputStringList));
    }
}
