package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransactionServiceImplTest {
    private static List<String> dataList;
    private static List<FruitTransaction> fruitTransactions;
    private static TransactionService dataParserService;

    @BeforeAll
    public static void setUp() {
        dataList = new ArrayList<>();
        fruitTransactions = new ArrayList<>();
        dataParserService = new TransactionServiceImpl();
    }

    @Test
    public void parseTransaction_ok() {
        fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50)
        );
        dataList = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        List<FruitTransaction> actualTransactions = dataParserService.parseTransactions(dataList);
        assertEquals(fruitTransactions.size(), actualTransactions.size());

        FruitTransaction actual;
        FruitTransaction expected;
        for (int i = 0; i < fruitTransactions.size(); ++i) {
            actual = actualTransactions.get(i);
            expected = fruitTransactions.get(i);
            assertEquals(expected.getFruit(), actual.getFruit());
            assertEquals(expected.getOperation(), actual.getOperation());
            assertEquals(expected.getQuantity(), actual.getQuantity());
        }
    }

    @Test
    public void parseTransactions_invalidString_notOk() {
        List<String> input = List.of(
                "b,banana,20",
                "invalid_row",
                "s,banana,100"
        );
        assertThrows(RuntimeException.class,
                () -> dataParserService.parseTransactions(input));
    }
}
