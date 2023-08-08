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
    private static final int INDEX = 0;
    private static final int EXP_QUANTITY = 10;
    private static final String FRUIT_NAME = "apple";
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
    public void convertData_successConverting_ok() {
        List<Fruit> result = dataConvertor.convertData(TEST_VALID_DATA);
        assertEquals(EXP_SIZE, result.size());
        assertEquals(FRUIT_NAME, result.get(INDEX).getFruit());
        assertEquals(Fruit.Operation.BALANCE, result.get(INDEX).getOperation());
        assertEquals(EXP_QUANTITY, result.get(INDEX).getQuantity());
    }

    @Test
    public void convertData_NullList_exceptionThrown() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConvertor.convertData(null));
    }

    @Test
    public void convertData_EmptyList_exceptionThrown() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConvertor.convertData(List.of()));
    }

    @Test
    public void convertData_InvalidSize_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConvertor.convertData(List.of(VALID_DATA)));
    }

    @Test
    public void convertData_InvalidQuantity_notOk() {
        assertThrows(NumberFormatException.class,
                () -> dataConvertor.convertData(TEST_INVALID_DATA));
    }
}
