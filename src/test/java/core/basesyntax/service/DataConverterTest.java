package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private static DataConverter dataConverter;
    private static List<String> listOfOkLines;
    private static List<String> listOfLineWithUnknownOperation;
    private static List<String> listOfLineWithQuantityNotNumber;
    private static List<String> nullList;
    private static List<String> missingFieldsList;
    private static List<String> invalidCaseOperation;
    private static List<String> listWithNull;
    private static List<String> negativeQuantityList;
    private static FruitTransaction fruitTransactionOne;
    private static FruitTransaction fruitTransactionTwo;
    private static FruitTransaction fruitTransactionThree;
    private static FruitTransaction fruitTransactionFour;
    private static List<FruitTransaction> fruitTransactionList;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
        listOfOkLines = List.of("b,banana,20", "s,apple,100", "p,banana,100", "r,apple,20");
        listOfLineWithUnknownOperation = List.of("l,apple,20");
        listOfLineWithQuantityNotNumber = List.of("r,apple,apple");
        nullList = null;
        missingFieldsList = List.of("p,apple");
        invalidCaseOperation = List.of("B,banana,20");
        listWithNull = Arrays.asList("b,banana,20", null, "p,apple,30");
        negativeQuantityList = List.of("p,apple,-10");
        fruitTransactionOne = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20);
        fruitTransactionTwo = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 100);
        fruitTransactionThree = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 100);
        fruitTransactionFour = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple", 20);
        fruitTransactionList = List.of(fruitTransactionOne,
                fruitTransactionTwo,
                fruitTransactionThree,
                fruitTransactionFour);
    }

    @Test
    void convertToTransaction_fruitTransaction_Ok() {
        assertEquals(fruitTransactionList, dataConverter.convertToTransaction(listOfOkLines));
    }

    @Test
    void convertToTransaction_nullList_notOk() {
        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(nullList);
        });
    }

    @Test
    void convertToTransaction_invalidLines_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(listOfLineWithUnknownOperation);
        });
    }

    @Test
    void convertToTransaction_emptyList_Ok() {
        List<String> emptyList = List.of();
        assertTrue(dataConverter.convertToTransaction(emptyList).isEmpty());
    }

    @Test
    void convertToTransaction_missingFields_notOk() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            dataConverter.convertToTransaction(missingFieldsList);
        });
    }

    @Test
    void convertToTransaction_caseInsensitiveOperation_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(invalidCaseOperation);
        });
    }

    @Test
    void convertToTransaction_listWithNull_notOk() {
        assertThrows(NullPointerException.class, () -> {
            dataConverter.convertToTransaction(listWithNull);
        });
    }

    @Test
    void convertToTransaction_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(negativeQuantityList);
        });
    }
}
