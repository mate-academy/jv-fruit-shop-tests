package core.basesyntax.operationhanlerservices;

import static core.basesyntax.services.OperationType.BALANCE;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitRecordDto;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class BalanceHandlerTest {
    private static BalanceHandler balanceHandler = new BalanceHandler();

    @Test
    public void apply_isOk() {
        Map<String, Integer> actual = new HashMap<>();
        FruitRecordDto fruitRecordDto = new FruitRecordDto(BALANCE, "banana", 100);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 100);
        balanceHandler.apply(fruitRecordDto, actual);
        assertEquals(expected, actual);
    }
}
