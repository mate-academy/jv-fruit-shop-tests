package core.basesyntax.strategy.operation;

import static core.basesyntax.strategy.OperationHandlersUtil.BALANCE_OPERATION_HANDLER;
import static core.basesyntax.strategy.OperationHandlersUtil.PURCHASE_OPERATION_HANDLER;
import static core.basesyntax.strategy.OperationHandlersUtil.RETURN_OPERATION_HANDLER;
import static core.basesyntax.strategy.OperationHandlersUtil.SUPPLY_OPERATION_HANDLER;
import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.Util;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlersTest {
    private static FruitTransaction balanceFruitTransaction;
    private static FruitTransaction purchaseFruitTransaction;
    private static FruitTransaction supplyFruitTransaction;
    private static FruitTransaction returnFruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        initializeFruitTransactions();
    }

    @After
    public void tearDown() {
        FruitStorage.fruitsMap.clear();
    }

    @Test
    public void handle_defaultCase_Ok() {
        BALANCE_OPERATION_HANDLER.handle(balanceFruitTransaction);
        int expectedBananas = 100;
        int actualBananas = FruitStorage.fruitsMap.get(Util.banana);
        assertEquals("Expected should be equal to "
                        + expectedBananas + " but was: "
                        + actualBananas,
                expectedBananas,
                actualBananas);
        PURCHASE_OPERATION_HANDLER.handle(purchaseFruitTransaction);
        expectedBananas = 80;
        actualBananas = FruitStorage.fruitsMap.get(Util.banana);
        assertEquals("Expected should be equal to "
                        + expectedBananas + " but was: "
                        + actualBananas,
                expectedBananas,
                actualBananas);
        SUPPLY_OPERATION_HANDLER.handle(supplyFruitTransaction);
        int expectedLemons = 50;
        int actualLemons = FruitStorage.fruitsMap.get(Util.lemon);
        assertEquals("Expected should be equal to "
                        + expectedLemons + " but was: "
                        + actualLemons,
                expectedLemons,
                actualLemons);
        RETURN_OPERATION_HANDLER.handle(returnFruitTransaction);
        int expectedApples = 30;
        int actualApples = FruitStorage.fruitsMap.get(Util.apple);
        assertEquals("Expected should be equal to "
                        + expectedApples + " but was: "
                        + actualApples,
                expectedApples,
                actualApples);
    }

    private static void initializeFruitTransactions() {
        balanceFruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                Util.banana, 100);
        purchaseFruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                Util.banana, 20);
        supplyFruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                Util.lemon, 50);
        returnFruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                Util.apple, 30);
    }
}
