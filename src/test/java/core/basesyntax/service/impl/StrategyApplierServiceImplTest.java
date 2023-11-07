package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionValidation;
import core.basesyntax.service.StrategyApplierService;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StrategyApplierServiceImplTest {
    public static final String DEFAULT_FRUIT = "banana";
    public static final String SECOND_DEFAULT_FRUIT = "apple";
    public static final int DEFAULT_QUANTITY = 100;
    public static final Operation DEFAULT_OPERATION = Operation.BALANCE;
    private StrategyApplierService strategyApplierService;
    private List<FruitTransaction> transactions;
    private OperationStrategy strategy;

    @BeforeEach
    void setUp() {
        transactions = new ArrayList<>();
        strategyApplierService = new StrategyApplierServiceImpl();
        transactions.add(new FruitTransaction(DEFAULT_OPERATION,
                DEFAULT_FRUIT, DEFAULT_QUANTITY));
        transactions.add(new FruitTransaction(DEFAULT_OPERATION,
                SECOND_DEFAULT_FRUIT, DEFAULT_QUANTITY));
        FruitTransactionValidation fruitValidator = new FruitTransactionValidationImpl();
        StorageDao storageDao = new StorageDaoImpl(fruitValidator);
        Map<Operation, OperationHandler> handlers = new HashMap<>();
        OperationHandler balanceHandler = new BalanceOperationHandler(storageDao);
        handlers.put(Operation.BALANCE, balanceHandler);
        strategy = new OperationStrategy(handlers);
    }

    @Test
    void applyStrategyWithNullParameters_NotOk() {
        assertThrows(NullPointerException.class,
                () -> strategyApplierService.applyMethod(null, null));
        assertThrows(NullPointerException.class,
                () -> strategyApplierService.applyMethod(transactions, null));
        assertThrows(NullPointerException.class,
                () -> strategyApplierService.applyMethod(null, strategy));
    }

    @Test
    void applyStrategyValidParameters_Ok() {
        assertDoesNotThrow(() -> strategyApplierService.applyMethod(transactions, strategy));
    }
}
