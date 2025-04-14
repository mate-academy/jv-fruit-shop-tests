package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    @Test
    void convertToTransaction_validInput_ok() {
        List<String> data = List.of("type,fruit,quantity", "b,banana,30");
        List<FruitTransaction> result = new DataConverterImpl().convertToTransaction(data);
        
        assertEquals(1, result.size());
        assertEquals(Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(30, result.get(0).getQuantity());
    }
    
    @Test
    void convertToTransaction_invalidQuantity_notOk() {
        List<String> data = List.of("type,fruit,quantity", "b,banana,xyz");
        
        assertThrows(IllegalArgumentException.class,
                () -> new DataConverterImpl().convertToTransaction(data));
    }
    
    @Test
    void convertToTransaction_emptyInput_ok() {
        List<String> data = List.of("type,fruit,quantity");
        List<FruitTransaction> result = new DataConverterImpl().convertToTransaction(data);
        
        assertTrue(result.isEmpty());
    }
}
