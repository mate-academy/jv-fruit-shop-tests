package core.basesyntax.service.impl;

import core.basesyntax.exeption.OperationNotFoundExeption;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.OperationMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationMapperTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String UNKNOWN_OPERATION = "w";
    private static OperationMapper operationMapper;

    @BeforeClass
    public static void setUp() {
        operationMapper = new OperationMapperImpl();
    }

    @Test
    public void mapOperation_balance_ok() {
        Assert.assertEquals(Transaction.Operation.BALANCE, operationMapper.mapToEnumValue(BALANCE));
    }

    @Test
    public void mapOperation_supply_ok() {
        Assert.assertEquals(Transaction.Operation.SUPPLY, operationMapper.mapToEnumValue(SUPPLY));
    }

    @Test
    public void mapOperation_purchase_ok() {
        Assert.assertEquals(Transaction.Operation.PURCHASE,
                operationMapper.mapToEnumValue(PURCHASE));
    }

    @Test
    public void mapOperation_return_ok() {
        Assert.assertEquals(Transaction.Operation.RETURN, operationMapper.mapToEnumValue(RETURN));
    }

    @Test(expected = OperationNotFoundExeption.class)
    public void mapOperation_unknownOperation_notOk() {
        Transaction.Operation operation = operationMapper.mapToEnumValue(UNKNOWN_OPERATION);
    }
}
