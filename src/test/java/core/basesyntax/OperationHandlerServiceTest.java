package core.basesyntax;

import core.basesyntax.db.HandlerStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.OperationHandlerService;
import core.basesyntax.service.impl.OperationHandlerServiceImpl;
import core.basesyntax.strategy.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.DecreaseAmountOperationHandlerImpl;
import core.basesyntax.strategy.IncreaseAmountOperationHandlerImpl;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OperationHandlerServiceTest {
    private static final Fruit APPLE = new Fruit("apple");
    private static final Integer APPLE_AMOUNT = 100;
    private static final Integer APPLE_AMOUNT_SUPPLIED = 20;
    private static final Integer APPLE_AMOUNT_PURCHASED = 30;
    private static final Integer APPLE_AMOUNT_TOO_MUCH_PURCHASED = 130;
    private static final Integer APPLE_AMOUNT_RETURNED = 25;
    private static final FruitRecord APPLE_BALANCE_RECORD = new FruitRecord(FruitRecord.Operation.BALANCE, APPLE, APPLE_AMOUNT);
    private static final FruitRecord APPLE_SUPPLY_RECORD = new FruitRecord(FruitRecord.Operation.SUPPLY, APPLE, APPLE_AMOUNT_SUPPLIED);
    private static final FruitRecord APPLE_PURCHASE_RECORD = new FruitRecord(FruitRecord.Operation.PURCHASE, APPLE, APPLE_AMOUNT_PURCHASED);
    private static final FruitRecord APPLE_PURCHASE_TOO_MUCH_RECORD = new FruitRecord(FruitRecord.Operation.PURCHASE, APPLE, APPLE_AMOUNT_TOO_MUCH_PURCHASED);
    private static final FruitRecord APPLE_RETURN_RECORD = new FruitRecord(FruitRecord.Operation.RETURN, APPLE, APPLE_AMOUNT_RETURNED);
    private static OperationHandlerService operationHandlerService;

    @BeforeEach
    public void initialize() {
        Map<FruitRecord.Operation, OperationHandler> handlerMap = Map.of(
                FruitRecord.Operation.PURCHASE, new DecreaseAmountOperationHandlerImpl(),
                FruitRecord.Operation.SUPPLY, new IncreaseAmountOperationHandlerImpl(),
                FruitRecord.Operation.BALANCE, new BalanceOperationHandlerImpl(),
                FruitRecord.Operation.RETURN, new IncreaseAmountOperationHandlerImpl()
        );
        operationHandlerService = new OperationHandlerServiceImpl(new HandlerStorage(handlerMap));
    }

    @Test
    public void changingAmountOfApplesByBalanceOperation_Ok() {
        operationHandlerService.changeAmount(APPLE_BALANCE_RECORD);
        Integer actual = Storage.getStorage().get(APPLE);
        Integer expected = APPLE_AMOUNT;
        assertEquals(actual, expected);
    }

    @Test
    public void changingAmountOfApplesBySupplyOperation_Ok() {
        operationHandlerService.changeAmount(APPLE_BALANCE_RECORD);
        operationHandlerService.changeAmount(APPLE_SUPPLY_RECORD);
        Integer actual = Storage.getStorage().get(APPLE);
        Integer expected = APPLE_AMOUNT + APPLE_AMOUNT_SUPPLIED;
        assertEquals(actual, expected);
    }

    @Test
    public void changingAmountOfApplesByPurchaseOperation_Ok() {
        operationHandlerService.changeAmount(APPLE_BALANCE_RECORD);
        operationHandlerService.changeAmount(APPLE_PURCHASE_RECORD);
        Integer actual = Storage.getStorage().get(APPLE);
        Integer expected = APPLE_AMOUNT - APPLE_AMOUNT_PURCHASED;
        assertEquals(actual, expected);
    }

    @Test
    public void changingAmountOfApplesByReturnOperation_Ok() {
        operationHandlerService.changeAmount(APPLE_BALANCE_RECORD);
        operationHandlerService.changeAmount(APPLE_RETURN_RECORD);
        Integer actual = Storage.getStorage().get(APPLE);
        Integer expected = APPLE_AMOUNT + APPLE_AMOUNT_RETURNED;
        assertEquals(actual, expected);
    }

    @Test
    public void changingAmountOfApplesByPurchaseOperationWhenStorageHasNotEnoughFruits_notOk() {
        operationHandlerService.changeAmount(APPLE_BALANCE_RECORD);
        assertThrows(RuntimeException.class, () -> operationHandlerService.changeAmount(APPLE_PURCHASE_TOO_MUCH_RECORD));
    }
}
