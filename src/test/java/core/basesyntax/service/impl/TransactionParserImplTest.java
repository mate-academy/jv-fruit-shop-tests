package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static TransactionParserImpl transactionParser;

    @BeforeAll
    static void beforeAll() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    void parse_ValidData_ok() {
        List<String> fruits = List.of("b,apple,10", "b,banana,15", "r,apple,5");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 15),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 5));
        List<FruitTransaction> actual = transactionParser.parse(fruits);
        assertEquals(expected, actual);
    }

    @Test
    void parse_EmptyList_ok() {
        List<String> fruits = List.of();
        List<FruitTransaction> expected = List.of();
        List<FruitTransaction> actual = transactionParser.parse(fruits);
        assertEquals(expected, actual);
    }

    @Test
    void parse_InvalidData_throwsException() {
        List<String> fruits = List.of("b,apple,r", "b,banana,15", "r,apple,s");
        assertThrows(IllegalArgumentException.class, () ->
                transactionParser.parse(fruits));
    }
}
