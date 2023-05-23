package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionParserServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserServiceTest {
    private static TransactionParserService parserService;

    @BeforeAll
    static void beforeAll() {
        parserService = new TransactionParserServiceImpl();
    }

    @Test
    public void getTransactions_validInput_ok() {
        List<String> validLines = List.of("type,fruit,quantity", "b,apple,100",
                "b,banana,20", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "r,banana,13");
        List<FruitTransaction> expected = List
                .of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                        new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                        new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                        new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                        new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 13));
        List<FruitTransaction> actual = parserService.getTransactions(validLines);
        assertEquals(expected, actual);
    }

    @Test
    public void getTransactions_unknownOperation_notOk() {
        List<String> unknownOperation = List.of("type,fruit,quantity", "c,apple,100");
        assertThrows(RuntimeException.class, () -> parserService.getTransactions(unknownOperation));
    }

    @Test
    public void getTransactions_nullList_notOk() {
        assertThrows(NullPointerException.class, () -> parserService.getTransactions(null));
    }
}
