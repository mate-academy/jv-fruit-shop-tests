package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static List<String> list;
    private static List<FruitTransaction> expected;
    private static DataConverter dataConverter;

    @BeforeAll
    static void beforeAll() {
        expected = new ArrayList<>();
        dataConverter = new DataConverterImpl();
        list = new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        list.clear();
        list.add("type,fruit,quantity");
        list.add("b,banana,20");
        list.add("b,apple,100");
        list.add("s,banana,100");
        list.add("p,banana,13");
        list.add("r,apple,10");
        list.add("s,banana,50");

        expected.clear();
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 13));
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 10));
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 50));
    }

    @Test
    void convertToTransaction_ValidData_Ok() {
        assertEquals(
                expected,
                dataConverter.convertToTransaction(list),
                "Test convertToTransaction_ValidData_Ok failed"
        );
    }

    @Test
    void convertToTransaction_InvalidOperationType_NotOk() {
        list.add("d,banana,100");
        assertThrows(
                IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(list),
                "Test convertToTransaction_InvalidOperationType_NotOk failed"
        );
    }

    @Test
    void convertToTransaction_InvalidQuantity_NotOk() {
        list.add("r,banana,notNumber");
        assertThrows(
                NumberFormatException.class,
                () -> dataConverter.convertToTransaction(list),
                "Test convertToTransaction_InvalidQuantity_NotOk failed"
        );
    }

    @Test
    void convertToTransaction_InvalidQuantityNegative_NotOk() {
        list.add("r,banana,-3");
        assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(list),
                "Test convertToTransaction_InvalidQuantityNegative_NotOk failed"
        );
    }

    @Test
    void convertToTransaction_InvalidTransactionFormat_NotOk() {
        list.add("OneLine");
        assertThrows(
                IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(list),
                "Test convertToTransaction_InvalidTransactionFormat_NotOk failed"
        );
    }
}
