package core.basesyntax.services.imps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceImpTest {
    private ParserService parserService;
    private List<FruitTransaction> fruitTransactionList;

    @BeforeEach
    void setUp() {
        parserService = new ParserServiceImp();
        fruitTransactionList = new ArrayList<>();
    }

    @Test
    void parse_validString_ok() {
        fruitTransactionList.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "pineapple", 1));
        fruitTransactionList.add(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 2));
        fruitTransactionList.add(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 3));
        fruitTransactionList.add(
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
        fruitTransactionList.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "pineapple", 1));
        fruitTransactionList.add(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 2));
        List<FruitTransaction> actual = parserService.parse(
                " type,fruit,quantity /b,pineapple,1 /s, banana,2");
        assertEquals(fruitTransactionList, actual);
    }

    @AfterEach
    void tearDown() {
        fruitTransactionList.clear();
    }
}
