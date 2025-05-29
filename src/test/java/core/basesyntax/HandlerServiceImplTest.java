package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.handler.BalanceOperation;
import core.basesyntax.service.handler.PurchaseOperation;
import core.basesyntax.service.handler.ReturnOperation;
import core.basesyntax.service.handler.SupplyOperation;
import core.basesyntax.service.handlerservice.HandlerService;
import core.basesyntax.service.handlerservice.HandlerServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class HandlerServiceImplTest {

    @Test
    void getHandler_returnsCorrectHandler() {
        HandlerService handlerService = new HandlerServiceImpl(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation()
        ));

        assertTrue(handlerService.getHandler(FruitTransaction.Operation
                .PURCHASE) instanceof PurchaseOperation);
    }

    @Test
    void getHandler_returnsAllTypesCorrectly() {
        HandlerService handlerService = new HandlerServiceImpl(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation()
        ));

        assertInstanceOf(BalanceOperation.class, handlerService
                .getHandler(FruitTransaction.Operation.BALANCE));
        assertInstanceOf(PurchaseOperation.class, handlerService
                .getHandler(FruitTransaction.Operation.PURCHASE));
        assertInstanceOf(ReturnOperation.class, handlerService
                .getHandler(FruitTransaction.Operation.RETURN));
        assertInstanceOf(SupplyOperation.class, handlerService
                .getHandler(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    void getHandler_notRegisteredOperation_returnsNull() {
        HandlerService handlerService = new HandlerServiceImpl(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation()
        ));

        assertNull(handlerService.getHandler(FruitTransaction.Operation.PURCHASE));
    }

    @Test
    void getHandler_emptyHandlerMap_returnsNull() {
        HandlerService handlerService = new HandlerServiceImpl(Map.of());

        assertNull(handlerService.getHandler(FruitTransaction.Operation.BALANCE));
    }
}
