package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverterImpl dataConverterImpl = new DataConverterImpl();
    private List<String> list = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        list.clear();
    }

    @Test
    void convertToTransaction_wrongInteger_notOk() {

        list.add("b, apple, banana");
        assertThrows(IllegalArgumentException.class, () ->
                dataConverterImpl.convertToTransaction(list));
    }

    @Test
    void convertToTransaction_wrongTransaction_notOk() {

        list.add("b, apple, 80, 20");
        assertThrows(IllegalArgumentException.class, () ->
                dataConverterImpl.convertToTransaction(list));
    }

    @Test
    void convertToTransaction_existingTransactions_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, "banana", 10));
        expected.add(new FruitTransaction(Operation.PURCHASE, "apple", 15));
        expected.add(new FruitTransaction(Operation.RETURN, "cherry", 20));
        expected.add(new FruitTransaction(Operation.SUPPLY, "strawberry", 25));
        list.add("b, banana, 10");
        list.add("p, apple, 15");
        list.add("r, cherry, 20");
        list.add("s, strawberry, 25");
        List<FruitTransaction> actual = dataConverterImpl.convertToTransaction(list);
        assertEquals(expected, actual);
    }

    @Test
    void convertTransaction_wrongCode_notOk() {
        list.add("a, apple, 80");
        assertThrows(IllegalArgumentException.class, () ->
                dataConverterImpl.convertToTransaction(list));
    }

    @Test
    void convertTransaction_emptyCode_notOk() {
        list.add(", apple, 80");
        assertThrows(IllegalArgumentException.class, () ->
                dataConverterImpl.convertToTransaction(list));
    }

    @Test
    void convertTransaction_emptyFruit_notOk() {
        list.add("b, , 80");
        assertThrows(IllegalArgumentException.class, () ->
                dataConverterImpl.convertToTransaction(list));
    }

    @Test
    void convertTransaction_emptyInput_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                dataConverterImpl.convertToTransaction(list));
    }

    @Test
    void convertTransaction_extraSpaces_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, "banana", 10));
        expected.add(new FruitTransaction(Operation.PURCHASE, "apple", 15));
        expected.add(new FruitTransaction(Operation.RETURN, "cherry", 20));
        list.add("   b     , banana, 10");
        list.add("p,      apple     , 15");
        list.add("r, cherry,      20    ");
        List<FruitTransaction> actual = dataConverterImpl.convertToTransaction(list);
        assertEquals(expected, actual);
    }

    @Test
    void convertTransaction_zeroQuantity_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, "banana", 0));
        list.add("b, banana, 0");
        assertEquals(expected, dataConverterImpl.convertToTransaction(list));
    }

    @Test
    void convertTransaction_nullInput_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                dataConverterImpl.convertToTransaction(null));
    }
}
