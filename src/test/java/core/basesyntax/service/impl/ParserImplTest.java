package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Parser;
import org.junit.jupiter.api.Test;

class ParserImplTest {
    private final Parser<FruitTransaction> parser = new ParserImpl();

    @Test
    void parse_transactionNull_notOk() {
        String expected = "Transaction string can't be null";
        assertEquals(expected, assertThrows(IllegalArgumentException.class,
                () -> parser.parse(null)).getMessage());
    }

    @Test
    void parse_wrongParts_notOk() {
        String wrongTransaction = "b,apple";
        String expected = "Transaction string must have 3 parts: "
                + "operation, fruit, quantity";
        assertEquals(expected, assertThrows(IllegalArgumentException.class,
                () -> parser.parse(wrongTransaction)).getMessage());
    }

    @Test
    void parse_wrongOperation_notOk() {
        String wrongOperation = "q,apple,100";
        String expected = "Unknown operation code: q";
        assertEquals(expected, assertThrows(IllegalArgumentException.class,
                () -> parser.parse(wrongOperation)).getMessage());
    }

    @Test
    void parse_fruitEmpty_notOk() {
        String fruitEmpty = "b,,100";
        String expected = "Fruit name can't be empty";
        assertEquals(expected, assertThrows(IllegalArgumentException.class,
                () -> parser.parse(fruitEmpty)).getMessage());
    }

    @Test
    void parse_quantityLessThanZero_notOk() {
        String quantityLessThanZero = "b,apple,-1";
        String expected = "Quantity can't be less than 0!";
        assertEquals(expected, assertThrows(IllegalArgumentException.class,
                () -> parser.parse(quantityLessThanZero)).getMessage());
    }

    @Test
    void parse_quantityNotNumber_notOk() {
        String quantityNotNumber = "b,apple,a";
        String expected = "Quantity must be a valid integer: a";
        assertEquals(expected, assertThrows(IllegalArgumentException.class,
                () -> parser.parse(quantityNotNumber)).getMessage());
    }

    @Test
    void parse_getTransaction_Ok() {
        String transaction = "b,apple,100";
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple",100);
        FruitTransaction actual = parser.parse(transaction);
        assertEquals(expected.getOperation(),actual.getOperation());
        assertEquals(expected.getFruit(),actual.getFruit());
        assertEquals(expected.getQuantity(),actual.getQuantity());
    }
}
