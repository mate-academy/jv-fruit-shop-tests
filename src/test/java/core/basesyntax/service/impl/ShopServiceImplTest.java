package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService service;
    private List<FruitTransaction> emptyListOfTransactions;
    private List<FruitTransaction> nullTransaction;

    @BeforeEach
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers
                = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                transaction -> {});
        service = new ShopServiceImpl(
                new OperationStrategyImpl(operationHandlers));
        emptyListOfTransactions = List.of();
        nullTransaction = new ArrayList<>();
        nullTransaction.add(null);
        nullTransaction.add(new FruitTransaction());
    }

    @Test
    public void process_nullListOfTransactions_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> service.process(null));
    }

    @Test
    public void process_emptyListOfTransactions_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> service.process(emptyListOfTransactions));
    }

    @Test
    public void process_nullTransaction_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> service.process(nullTransaction));
    }

    @Test
    public void process_normalList_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        List<FruitTransaction> normalList = List.of(transaction);
        assertDoesNotThrow(() -> service.process(normalList));
    }
}
