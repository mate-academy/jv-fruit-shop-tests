package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperation;
import core.basesyntax.strategy.operation.FruitOperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperation;
import core.basesyntax.strategy.operation.ReturnOperation;
import core.basesyntax.strategy.operation.SupplyOperation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final List<FruitTransaction> NORMAL_FRUIT_TRANSACTION_VALUES = List.of(
            FruitTransaction.of(FruitTransaction.Operation.BALANCE, "apple", 100),
            FruitTransaction.of(FruitTransaction.Operation.SUPPLY, "apple", 20));
    private static final List<FruitTransaction> FRUIT_TRANSACTION_WITH_INVALID_VALUES = List.of(
            FruitTransaction.of(FruitTransaction.Operation.BALANCE, "apple", 100),
            FruitTransaction.of(FruitTransaction.Operation.SUPPLY, "apple", -20));
    private static final List<FruitTransaction> EMPTY_LIST = List.of();
    private static final String FRUIT_NAME = "apple";
    private static final int EXPECTED_QUANTITY = 120;
    private static ShopService shopService;
    private static StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        Map<FruitTransaction.Operation, FruitOperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(storageDao),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(storageDao),
                FruitTransaction.Operation.RETURN, new ReturnOperation(storageDao),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(storageDao));
        shopService = new ShopServiceImpl(new OperationStrategyImpl(operationHandlers));
    }

    @Test
    void process_CorrectValue_Ok() {
        shopService.process(NORMAL_FRUIT_TRANSACTION_VALUES);
        int actual = storageDao.getFruit(FRUIT_NAME).getQuantity();
        int expected = EXPECTED_QUANTITY;
        assertEquals(expected,actual);
    }

    @Test
    void precess_IncorrectValue_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            shopService.process(FRUIT_TRANSACTION_WITH_INVALID_VALUES);
        });
    }

    @Test
    void process_EmptyList_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            shopService.process(EMPTY_LIST);
        });
    }
}
