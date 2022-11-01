package core.basesyntax.service.read.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.read.OperationMapper;
import org.junit.Test;

public class OperationMapperImplTest {
    private static final OperationMapper operationMapper = new OperationMapperImpl();
    private static final String BALANCE_OPERATION = "b";
    private static final String PURCHASE_OPERATION = "p";
    private static final String RETURN_OPERATION = "r";
    private static final String SUPPLY_OPERATION = "s";
    private static final String WRONG_OPERATION = "m";

    @Test
    public void mapToOperation_balance_ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = operationMapper.mapToOperation(BALANCE_OPERATION);
        assertEquals(expected, actual);
    }

    @Test
    public void mapToOperation_purchase_ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.PURCHASE;
        FruitTransaction.Operation actual = operationMapper.mapToOperation(PURCHASE_OPERATION);
        assertEquals(expected, actual);
    }

    @Test
    public void mapToOperation_return_ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.RETURN;
        FruitTransaction.Operation actual = operationMapper.mapToOperation(RETURN_OPERATION);
        assertEquals(expected, actual);
    }

    @Test
    public void mapToOperation_supply_ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        FruitTransaction.Operation actual = operationMapper.mapToOperation(SUPPLY_OPERATION);
        assertEquals(expected, actual);
    }

    @Test
    public void mapToOperation_wrongOperation_notOk() {
        assertThrows(RuntimeException.class, () -> operationMapper.mapToOperation(WRONG_OPERATION));
    }
}
