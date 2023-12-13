package core.basesyntax.service;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FruitTransactionMapperTest {

    @Test
    public void mapData_NullData_ThrowsRuntimeException() {
        FruitTransactionMapper mapper = new FruitTransactionMapper();
        assertThrows(RuntimeException.class, () -> mapper.mapData(null));
    }

    @Test
    public void mapData_EmptyData_ThrowsRuntimeException() {
        FruitTransactionMapper mapper = new FruitTransactionMapper();

        assertThrows(RuntimeException.class, () -> mapper.mapData(Arrays.asList()));
    }

    @Test
    public void mapData_ValidData_ReturnsFruitTransactions() {
        FruitTransactionMapper mapper = new FruitTransactionMapper();
        List<String> lines = Arrays.asList(
                "OPERATION,FRUIT,QUANTITY",
                "p,Apple,10",
                "r,Banana,5"
        );

        List<FruitTransaction> result = mapper.mapData(lines);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.PURCHASE, result.get(0).getOperation());
        assertEquals("Apple", result.get(0).getFruit());
        assertEquals(10, result.get(0).getQuantity());
        assertEquals(FruitTransaction.Operation.RETURN, result.get(1).getOperation());
        assertEquals("Banana", result.get(1).getFruit());
        assertEquals(5, result.get(1).getQuantity());
    }
}
