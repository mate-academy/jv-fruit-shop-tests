package service.converter;

import static model.FruitTransaction.Operation.BALANCE;
import static model.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static List<FruitTransaction> expected;
    private static DataConverter dataConverter;
    private static final String FIRST_FRUIT_FIELD = "banana";
    private static final String SECOND_FRUIT_FIELD = "orange";
    private static final int FRUIT_QUANTITY = 20;
    private final List<String> validList = new ArrayList<>(List.of(
            "fruit,quantity",
            "b,banana,20",
            "s,orange,20"
    ));
    private final List<String> invalidList = new ArrayList<>(List.of(
            "fruit,quantity",
            "banana,20,banana"
    ));

    @BeforeAll
    static void createExpectedList() {
        dataConverter = new DataConverterImpl();
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setOperation(BALANCE);
        firstTransaction.setFruit(FIRST_FRUIT_FIELD);
        firstTransaction.setQuantity(FRUIT_QUANTITY);
        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setOperation(SUPPLY);
        secondTransaction.setFruit(SECOND_FRUIT_FIELD);
        secondTransaction.setQuantity(FRUIT_QUANTITY);
        expected = new ArrayList<>(List.of(
                firstTransaction,
                secondTransaction
        ));
    }

    @Test
    void convert_valid_ok() {
        List<FruitTransaction> actual = dataConverter.convertToTransactionList(validList);
        assertEquals(expected, actual);
    }

    @Test
    void convert_nullInput_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransactionList(null));
    }

    @Test
    void convert_emptyList_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransactionList(Collections.emptyList()));
    }

    @Test
    void convert_invalidFormatList_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransactionList(invalidList));
    }
}
