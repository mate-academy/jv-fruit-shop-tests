package core.basesyntax.services.imps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceImpTest {
    private static ParserServiceImp parserService;

    @BeforeAll
    static void beforeAll() {
        parserService = new ParserServiceImp();
    }

    @Test
    void parse_validString_ok() {
        List<FruitTransaction> fruitTransactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "pineapple", 1),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 2),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 3),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 4));
        List<FruitTransaction> actual = parserService.parse(
                "type,fruit,quantity/b,pineapple,1/s,banana,2/p,apple,3/r,orange,4");
        assertEquals(fruitTransactionList, actual);
    }

    @Test
    void parse_emptyString_Ok() {
        List<FruitTransaction> parsedTransactions = parserService.parse("");
        assertTrue(parsedTransactions.isEmpty());
    }

    @Test
    void parse_stringWithSpaces_Ok() {
        List<FruitTransaction> fruitTransactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "pineapple", 1),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 2));
        List<FruitTransaction> actual = parserService.parse(
                " type,fruit,quantity /b,pineapple,1 /s, banana,2");
        assertEquals(fruitTransactionList, actual);
    }
}
