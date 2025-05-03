package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionConvertorTest {
    private static final String SEPARATOR = " ";
    private static TransactionConvertor transactionConvertor;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    @BeforeAll
    static void beforeAll() {
        transactionConvertor = new TransactionConvertor();
    }

    @Test
    void convertToTransactionsList_nullLines_notOk() {
        assertThrows(NullPointerException.class,
                () -> transactionConvertor.convertToTransactionsList(null));
    }

    @Test
    void convertToTransactionsList_normalLines_Ok() {
        String inputLine = "type,fruit,quantity, b,banana,20, b,apple,100, s,banana,100,"
                + " p,banana,13, r,apple,10, p,apple,20, p,banana,5, s,banana,50";
        List<String> inputFileLines = Arrays.stream(inputLine.split(SEPARATOR))
                .collect(Collectors.toList());
        List<FruitTransaction> fruitTransactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE, 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 50));
        assertEquals(fruitTransactionList,
                transactionConvertor.convertToTransactionsList(inputFileLines));
    }
}
