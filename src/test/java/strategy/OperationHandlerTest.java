package strategy;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private static final int POSITIVE_QUANTITY = 23;
    private static final int NEGATIVE_QUANTITY = -23;
    private static FruitTransaction transaction;
    private static OperationHandler balanceHandler;
    private static OperationHandler purchaseHandler;
    private static OperationHandler supplyHandler;
    private static OperationHandler returnHandler;

    @BeforeAll
    static void beforeAll() {
        transaction = new FruitTransaction("b", "apple", 23);
        balanceHandler = new BalanceOperation();
        purchaseHandler = new PurchaseOperation();
        supplyHandler = new SupplyOperation();
        returnHandler = new ReturnOperation();
    }

    @Test
    void getProperPositiveQuantity() {
        int actualBalanceQuantity = balanceHandler.getQuantityToCalculate(transaction);
        int actualSupplyQuantity = supplyHandler.getQuantityToCalculate(transaction);
        int actualReturnQuantity = returnHandler.getQuantityToCalculate(transaction);
        assertAll("Positive quantity for three operations",
                () -> assertEquals(
                        POSITIVE_QUANTITY, actualBalanceQuantity),
                () -> assertEquals(POSITIVE_QUANTITY, actualSupplyQuantity),
                () -> assertEquals(POSITIVE_QUANTITY, actualReturnQuantity));
    }

    @Test
    void getNegativeQuantityForPurchaseOperation() {
        int actualPurchaseQuantity =
                purchaseHandler.getQuantityToCalculate(transaction);
        assertEquals(NEGATIVE_QUANTITY, actualPurchaseQuantity);
    }
}
