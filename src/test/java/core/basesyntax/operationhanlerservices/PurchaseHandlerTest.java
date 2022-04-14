package core.basesyntax.operationhanlerservices;

import static core.basesyntax.services.OperationType.PURCHASE;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitRecordDto;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class PurchaseHandlerTest {
    private static PurchaseHandler purchaseHandler = new PurchaseHandler();

    @Test
    public void apply_isOk() {
        Map<String, Integer> actual = new HashMap<>();
        actual.put("banana", 100);
        FruitRecordDto fruitRecordDto = new FruitRecordDto(PURCHASE, "banana", 10);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 90);
        purchaseHandler.apply(fruitRecordDto, actual);
        assertEquals(expected, actual);
    }

    @Test
    public void apply_isNotOk() {
        Map<String, Integer> actual = new HashMap<>();
        actual.put("banana", 9);
        FruitRecordDto fruitRecordDto = new FruitRecordDto(PURCHASE, "banana", 10);
        Assertions.assertThrows(RuntimeException.class, () ->
                    purchaseHandler.apply(fruitRecordDto, actual));
    }
}
