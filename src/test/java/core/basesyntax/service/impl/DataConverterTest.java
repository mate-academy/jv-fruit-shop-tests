package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterTest {
    private DataConverter dataConverter = new DataConverterImpl();
    private List<String> badList
            = Arrays.asList("type,fruit,quantity", "ba,banana,20", "b,apple,//,55");

    @Test
    void convertToTransaction_wrongOperationFormat_NotOk() {
        badList = Arrays.asList("type,fruit,quantity", "ba,banana,20", "b,apple,55");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(badList),
                "bad format");
    }

    @Test
    void convertToTransaction_wrongNumberFormat_NotOk() {
        badList = Arrays.asList("type,fruit,quantity", "b,banana,==", "b,apple,55");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(badList),
                "bad format");
    }

    @Test
    void convertToTransaction_wrongQuantityFormat_NotOk() {
        badList = Arrays.asList("type,fruit,quantity", "b,banana,20", "b,apple,55,55");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(badList),
                "bad format");
    }

    @Test
    void convertToTransaction_Ok() {
        List<String> testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("b,banana,20");
        testList.add("b,apple,55");
        List<FruitTransaction> expected = Arrays.asList(new FruitTransaction(FruitTransaction
                .Operation.BALANCE,new Fruit("banana"),20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("apple"), 55));
        assertEquals(expected, dataConverter.convertToTransaction(testList));
    }

    @Test
    void convertToTransaction_supply_Ok() {
        List<String> testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("s,banana,20");
        testList.add("b,apple,55");
        List<FruitTransaction> expected = Arrays.asList(new FruitTransaction(FruitTransaction
                        .Operation.SUPPLY,new Fruit("banana"),20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("apple"), 55));
        assertEquals(expected, dataConverter.convertToTransaction(testList));
    }

    @Test
    void convertToTransaction_purchase_Ok() {
        List<String> testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("p,banana,20");
        testList.add("b,apple,55");
        List<FruitTransaction> expected = Arrays.asList(new FruitTransaction(FruitTransaction
                        .Operation.PURCHASE,new Fruit("banana"),20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("apple"), 55));
        assertEquals(expected, dataConverter.convertToTransaction(testList));
    }

    @Test
    void convertToTransaction_return_Ok() {
        List<String> testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("r,banana,20");
        testList.add("b,apple,55");
        List<FruitTransaction> expected = Arrays.asList(new FruitTransaction(FruitTransaction
                        .Operation.RETURN,new Fruit("banana"),20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("apple"), 55));
        assertEquals(expected, dataConverter.convertToTransaction(testList));
    }
}
