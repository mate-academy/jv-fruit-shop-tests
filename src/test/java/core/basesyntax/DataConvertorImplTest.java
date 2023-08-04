package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.DataConvertorImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConvertorImplTest {
    private static final int EXP_SIZE = 2;
    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;
    private static final int FIRST_EXP_QUANTITY = 15;
    private static final int SECOND_EXP_QUANTITY = 10;
    private static final String FIRST_FRUIT_NAME = "banana";
    private static final String SECOND_FRUIT_NAME = "apple";
    private static final String VALID_DATA = "b,apple,10";
    private static final List<String> TEST_INVALID_DATA = Arrays
            .asList("p,apple,10", "r,banana,fifteen");
    private static final List<String> TEST_VALID_DATA = Arrays
            .asList("type,fruit,quantity", "b,apple,10", "s,banana,15");
    private DataConvertorImpl dataConvertor;

    @BeforeEach
    public void setUp() {
        Storage.inputData.clear();
        dataConvertor = new DataConvertorImpl();
    }

    @Test
    public void testConvertData_Success() {
        List<Fruit> result = dataConvertor.convertData(TEST_VALID_DATA);
        assertEquals(EXP_SIZE, result.size());
        assertEquals(SECOND_FRUIT_NAME, result.get(FIRST_INDEX).getFruit());
        assertEquals(Fruit.Operation.BALANCE, result.get(FIRST_INDEX).getOperation());
        assertEquals(SECOND_EXP_QUANTITY, result.get(FIRST_INDEX).getQuantity());
        assertEquals(FIRST_FRUIT_NAME, result.get(SECOND_INDEX).getFruit());
        assertEquals(Fruit.Operation.SUPPLY, result.get(SECOND_INDEX).getOperation());
        assertEquals(FIRST_EXP_QUANTITY, result.get(SECOND_INDEX).getQuantity());
    }

    @Test
    public void testConvertData_NullList() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConvertor.convertData(null));
    }

    @Test
    public void testConvertData_EmptyList() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConvertor.convertData(List.of()));
    }

    @Test
    public void testConvertData_InvalidSize() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConvertor.convertData(List.of(VALID_DATA)));
    }

    @Test
    public void testConvertData_InvalidQuantity() {
        assertThrows(NumberFormatException.class,
                () -> dataConvertor.convertData(TEST_INVALID_DATA));
    }
}
