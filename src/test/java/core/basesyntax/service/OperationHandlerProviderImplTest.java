package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.service.OperationHandlerProvider;
import core.basesyntax.basesyntax.service.impl.OperationHandlerProviderImpl;
import core.basesyntax.basesyntax.strategy.BalanceOperation;
import core.basesyntax.basesyntax.strategy.OperationHandler;
import core.basesyntax.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.basesyntax.strategy.ReturnOperation;
import core.basesyntax.basesyntax.strategy.SupplyOperation;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationHandlerProviderImplTest {
    private static OperationHandlerProvider provider;

    @BeforeAll
    static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation()
        );
        provider = new OperationHandlerProviderImpl(handlers);
    }

    @Test
    void get_validOperation_ok() {
        OperationHandler handler = provider.get(FruitTransaction.Operation.SUPPLY);

        assertNotNull(handler);
        assertInstanceOf(SupplyOperation.class, handler);
    }

    @Test
    void get_purchaseOperation_ok() {
        OperationHandler handler = provider.get(FruitTransaction.Operation.PURCHASE);
        assertInstanceOf(PurchaseOperation.class, handler);
    }

    @Test
    void get_nullOperation_notOk() {
        assertThrows(RuntimeException.class, () -> provider.get(null));
    }
}
