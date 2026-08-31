package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopServiceImpl createShopService() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        return new ShopServiceImpl(new OperationStrategyImpl(handlers));
    }

    @AfterEach
    void tearDown() {
        Storage.internalMapForService().clear();
    }

    @Test
    void process_balance_ok() {
        ShopServiceImpl shopService = createShopService();
        shopService.process(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10)
        ));
        Assertions.assertEquals(10, Storage.internalMapForService().get("apple"));
    }

    @Test
    void process_supply_ok() {
        ShopServiceImpl shopService = createShopService();
        shopService.process(List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 5)
        ));
        Assertions.assertEquals(5, Storage.internalMapForService().get("apple"));
    }

    @Test
    void process_purchaseValid_ok() {
        ShopServiceImpl shopService = createShopService();
        shopService.process(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 5)
        ));
        Assertions.assertEquals(5, Storage.internalMapForService().get("apple"));
    }

    @Test
    void process_purchaseInvalid_notOk() {
        ShopServiceImpl shopService = createShopService();
        shopService.process(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 2)
        ));
        Assertions.assertThrows(RuntimeException.class,
                () -> shopService.process(List.of(
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 5)
                )));
    }

    @Test
    void process_return_ok() {
        ShopServiceImpl shopService = createShopService();
        shopService.process(List.of(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 3)
        ));
        Assertions.assertEquals(3, Storage.internalMapForService().get("apple"));
    }
}
