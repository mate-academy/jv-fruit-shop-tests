package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TransactionServiceImplTest {
    private static List<String> dataList;
    private static List<FruitTransaction> fruitTransactions;
    private static TransactionService dataParserService;

    @BeforeClass
    public static void setUp() {
        dataList = new ArrayList<>();
        fruitTransactions = new ArrayList<>();
        dataParserService = new TransactionServiceImpl();
    }

    @Before
    public void init() {
        dataList = Stream.of("type,fruit,quantity", "b,banana,20", "b,apple,100", "s,banana,100",
                        "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50")
                .collect(Collectors.toList());
        fruitTransactions = Stream.of(
                        new FruitTransaction(FruitTransaction.Operation.BALANCE,
                                "banana", 20),
                        new FruitTransaction(FruitTransaction.Operation.BALANCE,
                                "apple", 100),
                        new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                                "banana", 100),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                "banana", 13),
                        new FruitTransaction(FruitTransaction.Operation.RETURN,
                                "apple", 10),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                "apple", 20),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                "banana", 5),
                        new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                                "banana", 50))
                .collect(Collectors.toList());
    }

    @Test
    public void parseTransaction_ok() {
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

    @Test (expected = RuntimeException.class)
    public void invalidString_notOk() {
        dataList.add("invalid_transaction^&!@");
        dataParserService.parseTransactions(dataList);
    }
}
