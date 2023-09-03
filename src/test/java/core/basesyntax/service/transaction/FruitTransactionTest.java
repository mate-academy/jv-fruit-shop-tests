package core.basesyntax.service.transaction;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void getOperationByCode_correctCode_Ok() {
        String correctCodeB = "b";
        FruitTransaction.Operation firstActual
                = FruitTransaction.Operation.getOperationByCode(correctCodeB);
        assertEquals(firstActual, FruitTransaction.Operation.BALANCE);

        String correctCodeS = "s";
        FruitTransaction.Operation secondActual
                = FruitTransaction.Operation.getOperationByCode(correctCodeS);
        assertEquals(secondActual, FruitTransaction.Operation.SUPPLY);

        String correctCodeP = "p";
        FruitTransaction.Operation thirdActual
                = FruitTransaction.Operation.getOperationByCode(correctCodeP);
        assertEquals(thirdActual, FruitTransaction.Operation.PURCHASE);

        String correctCodeR = "r";
        FruitTransaction.Operation fourthActual
                = FruitTransaction.Operation.getOperationByCode(correctCodeR);
        assertEquals(fourthActual, FruitTransaction.Operation.RETURN);
    }

    @Test
    void getOperationByCode_incorrectCode_NotOk() {
        String wrongCodeCapitalLetter = "B";
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation firstActual
                    = FruitTransaction.Operation.getOperationByCode(wrongCodeCapitalLetter);
            assertEquals(firstActual, FruitTransaction.Operation.BALANCE);
        });

        String wrongCodeO = "o";
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation firstActual
                    = FruitTransaction.Operation.getOperationByCode(wrongCodeO);
            assertEquals(firstActual, FruitTransaction.Operation.BALANCE);
        });

        String wrongManySymbolsCode = "bspr";
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation firstActual
                    = FruitTransaction.Operation.getOperationByCode(wrongManySymbolsCode);
            assertEquals(firstActual, FruitTransaction.Operation.BALANCE);
        });

        String emptyCode = "";
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation firstActual
                    = FruitTransaction.Operation.getOperationByCode(emptyCode);
            assertEquals(firstActual, FruitTransaction.Operation.BALANCE);
        });

        String nullCode = null;
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation firstActual
                    = FruitTransaction.Operation.getOperationByCode(nullCode);
            assertEquals(firstActual, FruitTransaction.Operation.BALANCE);
        });
    }
}
