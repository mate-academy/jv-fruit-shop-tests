package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitOperation;
import core.basesyntax.service.inter.Mapper;
import org.junit.BeforeClass;
import org.junit.Test;

public class MapperImplTest {
    private static Mapper mapper;

    private static final int TYPE = 0;
    private static final int FRUIT = 1;
    private static final int QUANTITY = 2;

    @BeforeClass
    public static void beforeClass() throws Exception {
        mapper = new MapperImpl();
    }

    @Test
    public void correctValues_Ok() {
        String value = "b,banana,23";
        Object actual = mapper.map(value);
        String[] data = value.split(",");
        Object expected = new FruitOperation(data[TYPE],
                data[FRUIT], Integer.parseInt(data[QUANTITY]));
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void incorrectValues_NotOk() {
        mapper.map("b,21,346");
        mapper.map("b,banana");
    }
}
