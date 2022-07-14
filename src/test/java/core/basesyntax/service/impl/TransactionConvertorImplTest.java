package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionConvertorImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String FIRST_KEY = "banana";
    private static final String SECOND_KEY = "apple";
    private static List<FruitTransaction> expected;
    private static List<FruitTransaction> actual;

    @BeforeClass
    public static void beforeClass() {
        TransactionConvertorImpl transactionConvertor = new TransactionConvertorImpl();
        expected = new ArrayList<>(List.of(
                new FruitTransaction(BALANCE,FIRST_KEY,20),
                new FruitTransaction(SUPPLY,FIRST_KEY,100),
                new FruitTransaction(PURCHASE,FIRST_KEY,13),
                new FruitTransaction(RETURN,SECOND_KEY,10)));
        List<String> lines = new ArrayList<>(List.of(HEADER,
                "b," + FIRST_KEY + ",20",
                "s," + FIRST_KEY + ",100",
                "p," + FIRST_KEY + ",13",
                "r," + SECOND_KEY + ",10"));
        actual = transactionConvertor.getFruitTransactions(lines);
    }

    @Test
    public void getFruitTransactions_Ok() {
        IntStream.range(0, expected.size()).forEach(i -> {
            assertEquals(expected.get(i).getOperation(), actual.get(i).getOperation());
            assertEquals(expected.get(i).getFruit(), actual.get(i).getFruit());
            assertEquals(expected.get(i).getQuantity(), actual.get(i).getQuantity());
        });
    }

    @Test
    public void sizeFruitTransactions_Ok() {
        assertEquals(expected.size(), actual.size());
    }
}
