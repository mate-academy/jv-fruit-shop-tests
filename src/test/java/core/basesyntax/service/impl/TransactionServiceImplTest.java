package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TransactionServiceImplTest {
    private List<String> dataList;
    private List<FruitTransaction> fruitTransactions;
    private TransactionService dataParserService;

    @Before
    public void setUp() {
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
        Assertions.assertEquals(fruitTransactions.size(), actualTransactions.size());

        FruitTransaction actual;
        FruitTransaction expected;
        for (int i = 0; i < fruitTransactions.size(); ++i) {
            actual = actualTransactions.get(i);
            expected = fruitTransactions.get(i);
            Assertions.assertEquals(expected.getFruit(), actual.getFruit());
            Assertions.assertEquals(expected.getOperation(), actual.getOperation());
            Assertions.assertEquals(expected.getQuantity(), actual.getQuantity());
        }
    }

    @Test
    public void parseTransactions_invalidString_notOk() {
        List<String> input = List.of(
                "b,banana,20",
                "invalid_row",
                "s,banana,100"
        );
        Assertions.assertThrows(RuntimeException.class,
                () -> dataParserService.parseTransactions(input));
    }
}
