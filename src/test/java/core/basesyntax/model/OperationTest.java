package core.basesyntax.model;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {
    @Test
    void getByCode_validCode_Ok() {
        FruitTransaction.Operation actualBalanceOperation = Operation.getOperationByCode("b");
        Operation balanceOperation = Operation.BALANCE;
        assertEquals(balanceOperation, actualBalanceOperation);

        Operation actualSupplyOperation = Operation.getOperationByCode("s");
        Operation supplyOperation = Operation.SUPPLY;
        assertEquals(supplyOperation, actualSupplyOperation);

        Operation actualPurchaseOperation = Operation.getOperationByCode("p");
        Operation purchaseOperation = Operation.PURCHASE;
        assertEquals(purchaseOperation, actualPurchaseOperation);

        Operation actualReturnOperation = Operation.getOperationByCode("r");
        Operation returnOperation = Operation.RETURN;
        assertEquals(returnOperation, actualReturnOperation);
    }

    @Test
    void getByCode_notValidCode_notOk() {
        assertThrows(IllegalArgumentException.class, () -> Operation.getOperationByCode("B"));
        assertThrows(IllegalArgumentException.class, () -> Operation.getOperationByCode("n"));
    }
}
