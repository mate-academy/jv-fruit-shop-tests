package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Storage;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.strategy.BalanceOperation;
import core.basesyntax.service.strategy.PurchaseOperation;
import core.basesyntax.service.strategy.ReturnOperation;
import core.basesyntax.service.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {

    private Storage storage;
    private Map<FruitTransaction.Operation, OperationHandler> handler;
    private OperationStrategy strategy;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        handler = new HashMap<>();
        strategy = new OperationStrategyImpl(handler);
    }

    @Test
    void getHandler_balance_ok() {
        BalanceOperation balanceOperation = new BalanceOperation(storage);
        handler.put(FruitTransaction.Operation.BALANCE, balanceOperation);

        assertInstanceOf(BalanceOperation.class,
                strategy.getHandler(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void getHandler_purchase_ok() {
        PurchaseOperation purchaseOperation = new PurchaseOperation(storage);
        handler.put(FruitTransaction.Operation.PURCHASE, purchaseOperation);

        assertInstanceOf(PurchaseOperation.class,
                strategy.getHandler(FruitTransaction.Operation.PURCHASE));
    }

    @Test
    void getHandler_supply_ok() {
        SupplyOperation supplyOperation = new SupplyOperation(storage);
        handler.put(FruitTransaction.Operation.SUPPLY, supplyOperation);

        assertInstanceOf(SupplyOperation.class,
                strategy.getHandler(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    void getHandler_return_ok() {
        ReturnOperation returnOperation = new ReturnOperation(storage);;
        handler.put(FruitTransaction.Operation.RETURN, returnOperation);

        assertInstanceOf(ReturnOperation.class,
                strategy.getHandler(FruitTransaction.Operation.RETURN));
    }

    @Test
    void getHandler_null_ok() {
        assertNull(strategy.getHandler(null));
    }

}
