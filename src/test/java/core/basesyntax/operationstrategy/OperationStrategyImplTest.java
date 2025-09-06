package core.basesyntax.operationstrategy;

import core.basesyntax.db.Storage;
import core.basesyntax.fruittransactionhandler.BalanceHandler;
import core.basesyntax.fruittransactionhandler.OperationHandler;
import core.basesyntax.fruittransactionhandler.PurchaseHandler;
import core.basesyntax.fruittransactionhandler.ReturnHandler;
import core.basesyntax.fruittransactionhandler.SupplyHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.TypeOfTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OperationStrategyImplTest {
    public static final FruitTransaction fruitTransaction = new FruitTransaction();
    public static final Storage storage = new Storage();
    public static final Map<TypeOfTransaction, OperationHandler>
            operationHandlerMap = new HashMap<>();
    public static final OperationStrategyService operationStrategy =
            new OperationStrategyImpl(operationHandlerMap);

    @BeforeEach
    public void beforeEach() {
        fruitTransaction.setFruit("orange");
        fruitTransaction.setQuantity(40);
    }

    @Test
    void getHandler_getBalanceHandler_ok() {
        fruitTransaction.setTypeOfTransaction(TypeOfTransaction.B);
        operationHandlerMap.put(TypeOfTransaction.B, new BalanceHandler(storage));
        OperationHandler expectedHandler = new BalanceHandler(storage);
        assertEquals(expectedHandler.getClass(), operationStrategy
                .getHandler(fruitTransaction.getTypeOfTransaction()).getClass());
    }

    @Test
    void getHandler_getPurchaseHandler_ok() {
        fruitTransaction.setTypeOfTransaction(TypeOfTransaction.P);
        operationHandlerMap.put(TypeOfTransaction.P, new PurchaseHandler(storage));
        OperationHandler expectedHandler = new PurchaseHandler(storage);
        assertEquals(expectedHandler.getClass(), operationStrategy
                .getHandler(fruitTransaction.getTypeOfTransaction()).getClass());
    }

    @Test
    void getHandler_getReturnHandler_ok() {
        fruitTransaction.setTypeOfTransaction(TypeOfTransaction.R);
        operationHandlerMap.put(TypeOfTransaction.R, new ReturnHandler(storage));
        OperationHandler expectedHandler = new ReturnHandler(storage);
        assertEquals(expectedHandler.getClass(), operationStrategy
                .getHandler(fruitTransaction.getTypeOfTransaction()).getClass());
    }

    @Test
    void getHandler_getSupplyHandler_ok() {
        fruitTransaction.setTypeOfTransaction(TypeOfTransaction.S);
        operationHandlerMap.put(TypeOfTransaction.S, new SupplyHandler(storage));
        OperationHandler expectedHandler = new SupplyHandler(storage);
        Assertions.assertEquals(expectedHandler.getClass(), operationStrategy
                .getHandler(fruitTransaction.getTypeOfTransaction()).getClass());
    }

    @Test
    void getHandler_HandlerNotFound_notOk() {
        fruitTransaction.setTypeOfTransaction(TypeOfTransaction.S);
        operationHandlerMap.put(TypeOfTransaction.B, new BalanceHandler(storage));
        Assertions.assertThrows(RuntimeException.class, () -> operationStrategy
                        .getHandler(fruitTransaction.getTypeOfTransaction()));
    }

    @Test
    void getHandler_InputParameterIsNull_notOk() {
        Map<TypeOfTransaction, OperationHandler>
                operationHandlerMap = new HashMap<>();
        OperationStrategyService operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        Assertions.assertThrows(RuntimeException.class, () -> operationStrategy
                        .getHandler(null));
        }
}
