package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class DataConverterImplTest {
    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    public void convertToTransaction_nullList_notOk() {
        assertThrows(NullPointerException.class, () -> dataConverter.convertToTransaction(null));
    }

    @Test
    public void convertToTransaction_elementsList_Ok() {
        List<String> list = new ArrayList<>();
        list.add("type,fruit,quantity");
        list.add("b,banana,20");
        list.add("b,apple,100");
        list.add("s,banana,100");
        int expected = 3;
        int actual = dataConverter.convertToTransaction(list).size();
        assertEquals(expected,actual);
    }
}
