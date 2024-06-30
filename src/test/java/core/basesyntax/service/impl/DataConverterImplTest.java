package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.strategy.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverter converter = new DataConverterImpl();

    @Test
    void convert_correctConvert_ok() {
        List<String> correctList = new ArrayList<>();
        correctList.add("b,banana,20");
        correctList.add("s,orange,100");
        correctList.add("r,apple,100");
        correctList.add("p,apple,50");
        List<FruitTransaction> expectedFruitTransactionList = new ArrayList<>();
        expectedFruitTransactionList.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        expectedFruitTransactionList.add(new FruitTransaction(Operation.SUPPLY, "orange", 100));
        expectedFruitTransactionList.add(new FruitTransaction(Operation.RETURN, "apple", 100));
        expectedFruitTransactionList.add(new FruitTransaction(Operation.PURCHASE, "apple", 50));

        List<FruitTransaction> actual = converter.convert(correctList);

        assertEquals(expectedFruitTransactionList, actual);
    }

    @Test
    void convert_emptyList_NotOk() {
        ArrayList<String> emptyList = new ArrayList<>();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> converter.convert(emptyList));
    }

    @Test
    void convert_qtyNotNumber_NotOk() {
        List<String> incorrectList = new ArrayList<>();
        incorrectList.add("b,banana,ten");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> converter.convert(incorrectList));
    }

    @Test
    void convert_incorrectList_NotOk() {
        List<String> incorrectList = new ArrayList<>();
        incorrectList.add("b,banana,ten,fresh");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> converter.convert(incorrectList));
    }

    @Test
    void convert_invalidOperationCode_NotOk() {
        List<String> incorrectList = new ArrayList<>();
        incorrectList.add("x,banana,ten");
        assertThrows(IllegalArgumentException.class, () -> converter.convert(incorrectList));
    }
}
