package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Action;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private static final FruitTransaction FRUIT_TRANSACTION1 = new FruitTransaction(
            Action.BALANCE,"apple", 12);
    private static final FruitTransaction FRUIT_TRANSACTION2 = new FruitTransaction(
            Action.RETURN, "apple", 3);
    private static final List<String> testList = List.of("b,apple,12", "r,apple,3");
    private static final List<String> nullList = null;
    private static final DataConverter dataConverter = new DataConverterImpl();

    @Test
    public void dataConverter_ValidData_Ok() {
        List<FruitTransaction> actual = dataConverter.convertToTransactions(testList);
        assertEquals(actual, List.of(FRUIT_TRANSACTION1,FRUIT_TRANSACTION2));
    }

    @Test
    public void dataConvector_NullData_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransactions(nullList);
        });
    }
}
