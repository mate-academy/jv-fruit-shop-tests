package core.basesyntax.service;

import core.basesyntax.exceptions.DataException;
import core.basesyntax.exceptions.NullException;
import core.basesyntax.exceptions.OperationException;
import core.basesyntax.exceptions.QuantityException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataParserImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class DataParserTest {
    private static DataParser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new DataParserImpl();
    }

    @Test
    public void getData_properData_ok() {
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 10);
        FruitTransaction actual = parser.getData("b,banana,10");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getData_nullData_notOk() {
        Assertions.assertThrows(NullException.class,
                () -> parser.getData(null));
    }

    @Test
    public void getData_emptyOperation_notOk() {
        Assertions.assertThrows(DataException.class,
                () -> parser.getData(",banana,10"));
    }

    @Test
    public void getData_emptyFruit_notOk() {
        Assertions.assertThrows(DataException.class,
                () -> parser.getData("b,,10"));
    }

    @Test
    public void getData_emptyQuantity_notOk() {
        Assertions.assertThrows(DataException.class,
                () -> parser.getData("b,banana,"));
    }

    @Test
    public void getData_emptyQuantityAndOperation_notOk() {
        Assertions.assertThrows(DataException.class,
                () -> parser.getData("banana"));
    }

    @Test
    public void getData_emptyData_notOk() {
        Assertions.assertThrows(DataException.class,
                () -> parser.getData(""));
    }

    @Test
    public void getData_wrongOperation_notOk() {
        Assertions.assertThrows(OperationException.class,
                () -> parser.getData("f,banana,10"));
    }

    @Test
    public void getData_wrongQuantity_notOk() {
        Assertions.assertThrows(QuantityException.class,
                () -> parser.getData("b,apple,-1"));
    }
}
