package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.file.DataConverterImpl;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private DataConverterImpl converter = new DataConverterImpl();

    @Test
    void correctReturnFor_balanceOperation() {
        FruitTransaction.Operation b = converter.getOperationFromCode("b");
        assertEquals(b, FruitTransaction.Operation.BALANCE);
    }

    @Test
    void correctReturnFor_purchaseOperation() {
        FruitTransaction.Operation p = converter.getOperationFromCode("p");
        assertEquals(p, FruitTransaction.Operation.PURCHASE);
    }

    @Test
    void correctReturnFor_returnOperation() {
        FruitTransaction.Operation r = converter.getOperationFromCode("r");
        assertEquals(r, FruitTransaction.Operation.RETURN);
    }

    @Test
    void correctReturnFor_supplyOperation() {
        FruitTransaction.Operation s = converter.getOperationFromCode("s");
        assertEquals(s, FruitTransaction.Operation.SUPPLY);
    }

    @Test
    void throwsExceptionForInvalidCode() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    converter.getOperationFromCode("k");
                });
        assertEquals("Unknown code: k", exception.getMessage());
    }

}
