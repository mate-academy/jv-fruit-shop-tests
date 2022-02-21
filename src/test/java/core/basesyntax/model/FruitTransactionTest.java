package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionTest {
    private static final String EMPTY_OPERATION = "";
    private static final String VALID_OPERATION_BALANCE = "b";
    private static final String VALID_OPERATION_SUPPLY = "s";
    private static final String VALID_OPERATION_PURCHASE = "p";
    private static final String VALID_OPERATION_RETURN = "r";
    private static final String INVALID_OPERATION = "m";
    private static final FruitTransaction.Operation BALANCE
            = FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation SUPPLY
            = FruitTransaction.Operation.SUPPLY;
    private static final FruitTransaction.Operation PURCHASE
            = FruitTransaction.Operation.PURCHASE;
    private static final FruitTransaction.Operation RETURN
            = FruitTransaction.Operation.RETURN;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        fruitTransaction = new FruitTransaction();
    }

    @Test(expected = RuntimeException.class)
    public void getOperationType_operationNull_notOk() {
        fruitTransaction.getOperationType(null);
    }

    @Test(expected = RuntimeException.class)
    public void getOperationType_operationEmpty_notOk() {
        fruitTransaction.getOperationType(EMPTY_OPERATION);
    }

    @Test(expected = RuntimeException.class)
    public void getOperationType_invalidOperation_notOk() {
        fruitTransaction.getOperationType(INVALID_OPERATION);
    }

    @Test
    public void getOperationType_validOperationBalance_ok() {
        FruitTransaction.Operation actualOperation
                = fruitTransaction.getOperationType(VALID_OPERATION_BALANCE);
        assertEquals(BALANCE, actualOperation);
    }

    @Test
    public void getOperationType_validOperationSupply_ok() {
        FruitTransaction.Operation actualOperation
                = fruitTransaction.getOperationType(VALID_OPERATION_SUPPLY);
        assertEquals(SUPPLY, actualOperation);
    }

    @Test
    public void getOperationType_validOperationPurchase_ok() {
        FruitTransaction.Operation actualOperation
                = fruitTransaction.getOperationType(VALID_OPERATION_PURCHASE);
        assertEquals(PURCHASE, actualOperation);
    }

    @Test
    public void getOperationType_validOperationReturn_ok() {
        FruitTransaction.Operation actualOperation
                = fruitTransaction.getOperationType(VALID_OPERATION_RETURN);
        assertEquals(RETURN, actualOperation);
    }

}
