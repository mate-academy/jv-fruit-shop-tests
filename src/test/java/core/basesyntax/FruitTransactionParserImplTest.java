package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidFruitTransactionException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.impl.CsvFileParserImpl;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitTransactionParserImplTest {
    private static FruitTransactionParser fruitTransactionParser;

    @BeforeAll
    static void setUp() {
        fruitTransactionParser = new FruitTransactionParserImpl(new CsvFileParserImpl());
    }

    @Test
    void parse_validData_ok() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "p,banana,23",
                "r,banana,324"
        );

        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 23),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 324)
        );

        List<FruitTransaction> actual = fruitTransactionParser.parse(data);

        assertEquals(expected, actual, "Returned list must be equals with the expected!");
    }

    @Test
    void parse_invalidData_throwException() {
        List<String> invalidData = List.of(
                "g,bana,-23,34"
        );

        assertThrows(InvalidFruitTransactionException.class,
                () -> fruitTransactionParser.parse(invalidData),
                "Invalid data must throw InvalidFruitTransactionException!");
    }
}
