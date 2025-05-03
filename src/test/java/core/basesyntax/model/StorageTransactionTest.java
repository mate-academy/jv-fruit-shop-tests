package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class StorageTransactionTest {
    private static final String WRONG_CODE = "t";
    private static final String BALANCE_CODE = "b";
    private static final String SUPPLY_CODE = "s";
    private static final String RETURN_CODE = "r";
    private static final String PURCHASE_CODE = "p";
    private StorageTransaction.Operation balanceOperation;
    private StorageTransaction.Operation supplyOperation;
    private StorageTransaction.Operation returnOperation;
    private StorageTransaction.Operation purchaseOperation;

    @Before
    public void setUp() {
        balanceOperation = StorageTransaction.Operation.BALANCE;
        supplyOperation = StorageTransaction.Operation.SUPPLY;
        returnOperation = StorageTransaction.Operation.RETURN;
        purchaseOperation = StorageTransaction.Operation.PURCHASE;
    }

    @Test(expected = NoSuchElementException.class)
    public void getOperation_doNotHaveSuchOperation_notOk() {
        StorageTransaction.Operation.getOperation(WRONG_CODE);
        fail("You should trow NoSuchElementException when you don't have such operation");
    }

    @Test
    public void getOperation_hasBalanceCode_ok() {
        StorageTransaction.Operation actual =
                StorageTransaction.Operation.getOperation(BALANCE_CODE);
        assertEquals("Expected operation name: " + balanceOperation.name(),
                balanceOperation, actual);
    }

    @Test
    public void getOperation_hasReturnCode_ok() {
        StorageTransaction.Operation actual =
                StorageTransaction.Operation.getOperation(RETURN_CODE);
        assertEquals("Expected operation name: " + returnOperation.name(),
                returnOperation, actual);
    }

    @Test
    public void getOperation_hasPurchaseCode_ok() {
        StorageTransaction.Operation actual =
                StorageTransaction.Operation.getOperation(PURCHASE_CODE);
        assertEquals("Expected operation name: " + purchaseOperation.name(),
                purchaseOperation, actual);
    }

    @Test
    public void getOperation_hasSupplyCode_ok() {
        StorageTransaction.Operation actual =
                StorageTransaction.Operation.getOperation(SUPPLY_CODE);
        assertEquals("Expected operation name: " + supplyOperation.name(),
                supplyOperation, actual);
    }
}
