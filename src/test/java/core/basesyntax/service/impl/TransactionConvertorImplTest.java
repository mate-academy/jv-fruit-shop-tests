package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionConvertorImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String BANANA_KEY = "banana";
    private static final String APPLE_KEY = "apple";
    private static TransactionConvertorImpl transactionConvertor;
    private static List<FruitTransaction> expected;
    private static List<FruitTransaction> actual;

    @BeforeClass
    public static void beforeClass() {
        transactionConvertor = new TransactionConvertorImpl();
        expected = new ArrayList<>(List.of(
                new FruitTransaction(BALANCE, BANANA_KEY,20),
                new FruitTransaction(SUPPLY, BANANA_KEY,100),
                new FruitTransaction(PURCHASE, BANANA_KEY,13),
                new FruitTransaction(RETURN, APPLE_KEY,10)));
    }

    @Test
    public void getFruitTransactions_Ok() {
        List<String> lines = new ArrayList<>(List.of(HEADER,
                "b," + BANANA_KEY + ",20",
                "s," + BANANA_KEY + ",100",
                "p," + BANANA_KEY + ",13",
                "r," + APPLE_KEY + ",10"));
        actual = transactionConvertor.getFruitTransactions(lines);
        IntStream.range(0, expected.size()).forEach(i -> {
            assertEquals(expected.get(i).getOperation(), actual.get(i).getOperation());
            assertEquals(expected.get(i).getFruit(), actual.get(i).getFruit());
            assertEquals(expected.get(i).getQuantity(), actual.get(i).getQuantity());
        });
    }

    @Test
    public void otherLetter_NotOk() {
        List<String> lines = new ArrayList<>(List.of(HEADER,
                "g," + BANANA_KEY + ",20"));
        assertThrows(RuntimeException.class,
                () -> transactionConvertor.getFruitTransactions(lines));
    }

    @Test
    public void sizeFruitTransactions_Ok() {
        assertEquals(expected.size(), actual.size());
    }
}
