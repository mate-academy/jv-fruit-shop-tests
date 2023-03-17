package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransactionTest {
    private static Transaction transaction;
    private static final String FRUIT = "banana";
    private static final String VALID_OPERATION_TYPE = "BALANCE";
    private static final String INVALID_OPERATION_TYPE = "Operation code";
    private static final String CODE_BALANCE = "b";
    private static final String CODE_RETURN = "r";
    private static final String CODE_SUPPLY = "s";
    private static final String CODE_PURCHASE = "p";
    private static final int AMOUNT = 15;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        transaction = new Transaction(Transaction.Operation.BALANCE, FRUIT, AMOUNT);
    }

    @Test
    public void getByCode_invalidCodePassing_notOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Code is invalid");
        Transaction.Operation.getByCode(INVALID_OPERATION_TYPE);
    }

    @Test
    public void transactionConstructor_initializationData_Ok() {
        assertEquals(FRUIT, transaction.getFruit());
        assertEquals(Transaction.Operation.BALANCE, transaction.getOperation());
        assertEquals(AMOUNT, transaction.getQuantity());
    }

    @Test
    public void getByCode_operationBalanceOk() {
        assertEquals(Transaction.Operation.BALANCE, Transaction.Operation.getByCode(CODE_BALANCE));
    }

    @Test
    public void getByCode_operationSupply_Ok() {
        assertEquals(Transaction.Operation.SUPPLY, Transaction.Operation.getByCode(CODE_SUPPLY));
    }

    @Test
    public void getByCode_operationPurchase_Ok() {
        assertEquals(Transaction.Operation.PURCHASE,
                Transaction.Operation.getByCode(CODE_PURCHASE));
    }

    @Test
    public void getByCode_operationReturn_Ok() {
        assertEquals(Transaction.Operation.RETURN, Transaction.Operation.getByCode(CODE_RETURN));
    }

    @Test
    public void getCode_byValueOf_Ok() {
        assertEquals(CODE_BALANCE, Transaction.Operation.valueOf(VALID_OPERATION_TYPE).getCode());
    }
}

