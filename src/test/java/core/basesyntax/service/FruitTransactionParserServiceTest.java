package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitTransactionParserServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionParserServiceTest {
    private static final String EXCEPTION_MESSAGE = "Exception should be thrown here";
    private FruitTransactionParserService fruitTransactionParser;

    @BeforeEach
    void setUp() {
        fruitTransactionParser = new FruitTransactionParserServiceImpl();
    }

    @Test
    void parseToFruitTransaction_validData_Ok() {
        List<String> validData = List.of("type,fruit,quantity", "b,banana,20", "b,apple,30");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 30));
        List<FruitTransaction> actual = fruitTransactionParser.parseToFruitTransaction(validData);
        assertEquals(expected, actual);
    }

    @Test
    void parseToFruitTransaction_invalidOperation_NotOk() {
        List<String> invalidData = List.of("type,fruit,quantity", "u,677,banana", "r,23fd,30");
        assertThrows(IllegalArgumentException.class, () ->
                fruitTransactionParser.parseToFruitTransaction(invalidData), EXCEPTION_MESSAGE);
    }

    @Test
    void parseToFruitTransaction_invalidQuantity_NotOk() {
        List<String> invalidQuantity = List.of("type,fruit,quantity", "b,677,banana",
                "r,23fd,yhh");
        assertThrows(NumberFormatException.class, () ->
                fruitTransactionParser.parseToFruitTransaction(invalidQuantity),
                EXCEPTION_MESSAGE);
    }

    @Test
    void parseToFruitTransaction_nullInput_NotOk() {
        assertThrows(NullPointerException.class, () ->
                fruitTransactionParser.parseToFruitTransaction(null), EXCEPTION_MESSAGE);
    }
}
