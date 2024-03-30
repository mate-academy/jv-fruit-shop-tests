package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionConvertorTest {
    private static String SEPARATOR = " ";
    private static TransactionConvertor transactionConvertor;
    private static List<String> inputFileLines;

    @BeforeAll
    static void beforeAll() {
        transactionConvertor = new TransactionConvertor();
    }

    @Test
    void convertToTransactionsList_nullLines_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> transactionConvertor.convertToTransactionsList(null));
    }

    @Test
    void convertToTransactionsList_normalLines_Ok() {
        String inputLine = "type,fruit,quantity, b,banana,20, b,apple,100, s,banana,100,"
                + " p,banana,13, r,apple,10, p,apple,20, p,banana,5, s,banana,50";
        inputFileLines = Arrays.stream(inputLine.split(SEPARATOR)).collect(Collectors.toList());
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 100));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 13));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple", 10));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 20));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 5));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 50));
        Assertions.assertEquals(fruitTransactionList,
                transactionConvertor.convertToTransactionsList(inputFileLines));
    }
}
