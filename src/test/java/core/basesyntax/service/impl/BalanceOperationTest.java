package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperation;
import org.junit.Test;

public class BalanceOperationTest {
    private final FruitOperation balanceOperation = new BalanceOperation();

    @Test
    public void applyBalanceOperation_Ok() {
        FruitRecordDto fruitRecordDto = new FruitRecordDto(OperationType.BALANCE, "banana", 100);
        int expected = 100;
        int actual = balanceOperation.apply(fruitRecordDto);
        assertEquals(expected, actual);
    }
}
