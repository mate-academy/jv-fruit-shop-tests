package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURRCHASE = "p";
    private static final String RETURN = "r";

    @Test
    void getOperationByValidCode_Ok() {
        assertAll(
                () -> assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation
                        .getByCode(BALANCE)),
                () -> assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation
                        .getByCode(SUPPLY)),
                () -> assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation
                        .getByCode(PURRCHASE)),
                () -> assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation
                        .getByCode(RETURN))
        );
    }

    @Test
    void getOperationByInvalidCode_notOk() {
        String codeNull = null;
        String wrongCode1 = "";
        String wrongCode2 = "w";
        String wrongCode3 = "abc";
        assertAll("Incorrect code of operation",
                () -> assertThrows(RuntimeException.class, () -> FruitTransaction.Operation
                        .getByCode(codeNull)),
                () -> assertThrows(RuntimeException.class, () -> FruitTransaction.Operation
                        .getByCode(wrongCode1)),
                () -> assertThrows(RuntimeException.class, () -> FruitTransaction.Operation
                        .getByCode(wrongCode2)),
                () -> assertThrows(RuntimeException.class, () -> FruitTransaction.Operation
                        .getByCode(wrongCode3))
        );
    }
}
