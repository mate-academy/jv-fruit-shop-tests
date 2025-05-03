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
    private static final FruitTransaction.Operation balance = FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation aReturn = FruitTransaction.Operation.RETURN;
    private static final FruitTransaction.Operation supply = FruitTransaction.Operation.SUPPLY;
    private static final FruitTransaction.Operation purchase = FruitTransaction.Operation.PURCHASE;

    private DataConverter dataConverter = new DataConverterImpl();
    private List<FruitTransaction> expected;

    @Test
    void convertToTransaction_wrongOperationFormat_notOk() {
        List<String> list = Arrays.asList("type,fruit,quantity", "ba,banana,20", "b,apple,55");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(list),
                "bad operation format");
    }

    @Test
    void convertToTransaction_wrongNumberFormat_notOk() {
        List<String> list = Arrays.asList("type,fruit,quantity", "b,banana,==", "b,apple,55");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(list),
                "bad number format");
    }

    @Test
    void convertToTransaction_wrongQuantityFormat_notOk() {
        List<String> list = Arrays.asList("type,fruit,quantity", "b,banana,20", "b,apple,55,55");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(list),
                "bad quantity format");
    }

    @Test
    void convertToTransaction_allOperations_ok() {
        List<String> testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("b,banana,20");
        testList.add("s,banana,20");
        testList.add("p,banana,20");
        testList.add("r,banana,20");
        expected = Arrays.asList(new FruitTransaction(balance, new Fruit("banana"), 20),
                new FruitTransaction(supply, new Fruit("banana"), 20),
                new FruitTransaction(purchase, new Fruit("banana"), 20),
                new FruitTransaction(aReturn, new Fruit("banana"), 20));
        assertEquals(expected, dataConverter.convertToTransaction(testList));
    }
}
