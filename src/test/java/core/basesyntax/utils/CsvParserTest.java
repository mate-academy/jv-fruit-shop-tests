package core.basesyntax.utils;

import static core.basesyntax.transactions.FruitsTransaction.Operation.BALANCE;
import static core.basesyntax.transactions.FruitsTransaction.Operation.PURCHASE;
import static core.basesyntax.transactions.FruitsTransaction.Operation.RETURN;
import static core.basesyntax.transactions.FruitsTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.CsvParseException;
import core.basesyntax.transactions.FruitsTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvParserTest {
    @Test
    void parseTransaction_ok() {
        List<String> lines = List.of(
                "operation,fruit,quantity",
                "b,apple,100",
                "s,apple,20",
                "p,apple,10",
                "r,apple,5"
        );
        List<FruitsTransaction> transactions = CsvParser.parseTransactions(lines);
        assertEquals(4, transactions.size());
        assertEquals(new FruitsTransaction(BALANCE, "apple", 100), transactions.get(0));
        assertEquals(new FruitsTransaction(SUPPLY, "apple", 20), transactions.get(1));
        assertEquals(new FruitsTransaction(PURCHASE, "apple", 10), transactions.get(2));
        assertEquals(new FruitsTransaction(RETURN, "apple", 5), transactions.get(3));
    }

    @Test
    void parseTransactions_invalidLine_notOk() {
        List<String> lines = List.of(
                "operation,fruit,quantity",
                "b,apple,100",
                "invalid,line"
        );
        assertThrows(CsvParseException.class,
                () -> CsvParser.parseTransactions(lines));
    }

    @Test
    void parseTransactions_emptyLines_notOk() {
        List<String> lines = List.of();
        assertThrows(CsvParseException.class,
                () -> CsvParser.parseTransactions(lines));
    }

    @Test
    void parseTransaction_nullLines_notOk() {
        List<String> lines = null;
        var csvParseException = assertThrows(CsvParseException.class,
                () -> CsvParser.parseTransactions(lines));
        assertEquals("Empty or null input line",
                csvParseException.getMessage());
    }

    @Test
    void parseTransaction_invalidOperation_notOk() {
        List<String> lines = List.of(
                "operation,fruit,quantity",
                "x, apple,100"
        );
        assertThrows(CsvParseException.class,
                () -> CsvParser.parseTransactions(lines));
    }

    @Test
    void parseTransaction_invalidQuantity_notOk() {
        List<String> lines = List.of(
                "operation,fruit,quantity",
                "b,apple,abc"
        );
        var csvParseException = assertThrows(CsvParseException.class,
                () -> CsvParser.parseTransactions(lines));
        assertEquals("Invalid quantity format: abc",
                csvParseException.getMessage());
    }
}
