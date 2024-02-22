package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;
import service.Parser;

class FruitTransactionParserTest {

    private static List<FruitTransaction> FRUIT_TRANSACTIONS = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "pamelo", 100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "pamelo", 54),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "pamelo", 30),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "pamelo", 6)
    );

    @Test
    void parse_Ok() {
        List<String> parseTest = List.of("type,fruit,quantity",
                "b,pamelo,100",
                "s,pamelo,54",
                "p,pamelo,30",
                "r,pamelo,6");

        Parser parser = new FruitTransactionParser();
        List<FruitTransaction> actual = parser.parse(parseTest);
        List<FruitTransaction> expected = FRUIT_TRANSACTIONS;
        assertEquals(expected, actual);
    }

    @Test
    void parse_NotOk() {
        Parser parser = new FruitTransactionParser();
        assertThrows(RuntimeException.class, () -> {
            parser.parse(List.of("badText"));
        });

        List<String> firstTest = List.of("type,fruit,quantity",
                "y,pamelo,100",
                "y,pamelo,54",
                "y,pamelo,30",
                "y,pamelo,6");

        assertThrows(RuntimeException.class, () -> {
            parser.parse(firstTest);
        });

        List<String> secondTest = List.of("type,fruit,quantity",
                "b,pamelo,BadNumber",
                "s,pamelo,54",
                "p,pamelo,30",
                "r,pamelo,6");
        assertThrows(RuntimeException.class, () -> {
            parser.parse(secondTest);
        });
    }
}
