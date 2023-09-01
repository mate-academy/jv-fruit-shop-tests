package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationTest {
    @Test
    void getType_recognitionOfExistingOperation_Ok() {
        String balanceOperation = "b";
        String supplyOperation = "s";
        String purchaseOperation = "p";
        String returnOperation = "r";
        Operation expectedBalanceOperation = Operation.BALANCE;
        Operation expectedSupplyOperation = Operation.SUPPLY;
        Operation expectedPurchaseOperation = Operation.PURCHASE;
        Operation expectedReturnOperation = Operation.RETURN;

        Assertions.assertEquals(expectedBalanceOperation, Operation.getType(balanceOperation));
        Assertions.assertEquals(expectedSupplyOperation, Operation.getType(supplyOperation));
        Assertions.assertEquals(expectedPurchaseOperation, Operation.getType(purchaseOperation));
        Assertions.assertEquals(expectedReturnOperation, Operation.getType(returnOperation));
    }

    @Test
    void getType_recognitionOfNoneExistingOperation_Ok() {
        String noneExistingOperation = "w";
        String emptyOperation = "";
        String blankOperation = " ";

        Assertions.assertThrows(RuntimeException.class,
                () -> Operation.getType(noneExistingOperation));
        Assertions.assertThrows(RuntimeException.class,
                () -> Operation.getType(emptyOperation));
        Assertions.assertThrows(RuntimeException.class,
                () -> Operation.getType(blankOperation));
    }
}
