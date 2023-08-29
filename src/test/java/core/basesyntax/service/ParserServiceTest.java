package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParserServiceTest {

    private static final List<String> INPUT_DATA =
            List.of("type,fruit,quantity",
                    "b,banana,20",
                    "b,apple,100",
                    "s,banana,100",
                    "p,banana,13",
                    "r,apple,10",
                    "p,apple,20",
                    "p,banana,5",
                    "s,banana,50");
    private static final List<FruitTransaction> EXPECTED_FRUIT_TRANSACTIONS =
            List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                    new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                    new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                    new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                    new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                    new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                    new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                    new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
    private static final String HEAD = "type,fruit,quantity";
    private static ParserService parserService;

    @BeforeAll
    static void beforeAll() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parse_listEmpty_notOk() {
        assertThrows(RuntimeException.class, () -> parserService.parse(new ArrayList<>()));
    }

    @Test
    void parse_lineToFruitTransaction_ok() {
        String line = "b,banana,100";
        List<FruitTransaction> fruitTransactionsExpected =
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100));
        List<FruitTransaction> fruitTransactionsActual = parserService.parse(List.of(HEAD, line));
        assertEquals(fruitTransactionsExpected, fruitTransactionsActual);
    }

    @Test
    void parse_8linesTo8FruitTransactions_ok() {
        List<FruitTransaction> fruitTransactionsActual = parserService.parse(INPUT_DATA);
        assertEquals(EXPECTED_FRUIT_TRANSACTIONS, fruitTransactionsActual);
    }
}
