package core.basesyntax.service.parser;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserCsvTest {
    private final ParserCsv parser = new ParserCsvImpl();
    private final List<String> lines = List.of(
            "type,fruit,quantity",
            "b,apple,100",
            "s,banana,90",
            "p,apple,20");
    private final List<FruitTransaction> fruitList = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 90),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));

    @Test
    void parseTransaction_ThreeLines_Ok() {
        List<FruitTransaction> actual = parser.parseTransactions(lines);
        Assertions.assertEquals(fruitList, actual);
    }

    @Test
    void parseTransaction_OneLines_Ok() {
        Assertions.assertThrows(RuntimeException.class,
                () -> parser.parseTransactions(List.of("type,fruit,quantity")));
    }

    @Test
    void parseTransaction_null_NotOK() {
        Assertions.assertThrows(RuntimeException.class,
                () -> parser.parseTransactions(null));
    }

    @Test
    void parseTransaction_EmptyList_NotOK() {
        Assertions.assertThrows(RuntimeException.class,
                () -> parser.parseTransactions(List.of()));
    }
}
