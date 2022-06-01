package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    public void findOperatorBalanceByLetter_ok() {
        FruitTransaction.Operation exceptedBalance = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actualBalance =
                FruitTransaction.Operation.findOperationByLetter("b");
        assertEquals(exceptedBalance, actualBalance);
    }

    @Test
    public void findOperatorSupplyByLetter_ok() {
        FruitTransaction.Operation exceptedSupply =
                FruitTransaction.Operation.SUPPLY;
        FruitTransaction.Operation actualSupply =
                FruitTransaction.Operation.findOperationByLetter("s");
        assertEquals(exceptedSupply, actualSupply);
    }

    @Test
    public void findOperatorPurchaseByLetter_ok() {
        FruitTransaction.Operation exceptedPurchase =
                FruitTransaction.Operation.PURCHASE;
        FruitTransaction.Operation actualPurchase =
                FruitTransaction.Operation.findOperationByLetter("p");
        assertEquals(exceptedPurchase, actualPurchase);
    }

    @Test
    public void findOperatorReturnByLetter_ok() {
        FruitTransaction.Operation exceptedReturn =
                FruitTransaction.Operation.RETURN;
        FruitTransaction.Operation actualReturn =
                FruitTransaction.Operation.findOperationByLetter("r");
        assertEquals(exceptedReturn,actualReturn);
    }

    @Test
    public void findOperator_ByLetter_NonExistOperator_notOK() {
        assertThrows(RuntimeException.class,
                () -> FruitTransaction.Operation.findOperationByLetter("l"));
    }

    @Test
    public void findOperator_ByNumber_NonExistOperator_notOK() {
        assertThrows(RuntimeException.class,
                () -> FruitTransaction.Operation.findOperationByLetter("6"));
    }

    @Test
    public void findOperator_ByCharacter_NonExistOperator_notOK() {
        assertThrows(RuntimeException.class,
                () -> FruitTransaction.Operation.findOperationByLetter("#"));
    }

    @Test
    public void findOperator_ByNull_NonExistOperator_notOK() {
        assertThrows(RuntimeException.class,
                () -> FruitTransaction.Operation.findOperationByLetter(null));
    }

    @Test
    public void findOperator_ByEmpty_NonExistOperator_notOK() {
        assertThrows(RuntimeException.class,
                () -> FruitTransaction.Operation.findOperationByLetter(""));
    }

}
