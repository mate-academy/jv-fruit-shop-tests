package core.basesyntax.serviceimpltest;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionServiceImplTest {

    @Test
    void valid_inputValue_B_Transaction_Ok() {
        String inputTitleB = "b";
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByCode(inputTitleB);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void valid_inputValue_P_Transaction_Ok() {
        String inputTitleP = "p";
        FruitTransaction.Operation expected = FruitTransaction.Operation.PURCHASE;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByCode(inputTitleP);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void valid_inputValue_S_Transaction_Ok() {
        String inputTitleS = "s";
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByCode(inputTitleS);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void valid_inputValue_R_Transaction_Ok() {
        String inputTitleR = "r";
        FruitTransaction.Operation expected = FruitTransaction.Operation.RETURN;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByCode(inputTitleR);
        Assertions.assertEquals(expected, actual);
    }
}
