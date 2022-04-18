package core.basesyntax.service;

import core.basesyntax.exceptions.OperationException;
import core.basesyntax.exceptions.QuantityException;
import core.basesyntax.model.FruitTransaction;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class DataParserTest {

    @Test
    public void getData_properData_ok() {
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 10);
        FruitTransaction actual = DataParser.getData("b,banana,10");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getData_nullData_ok() {
        Assertions.assertThrows(NullPointerException.class,
                () -> DataParser.getData(null));
    }

    @Test
    public void getData_wrongOperation_notOk() {
        Assertions.assertThrows(OperationException.class,
                () -> DataParser.getData("f,banana,10"));
    }

    @Test
    public void getData_wrongQuantity_notOk() {
        Assertions.assertThrows(QuantityException.class,
                () -> DataParser.getData("b,apple,-1"));
    }
}
