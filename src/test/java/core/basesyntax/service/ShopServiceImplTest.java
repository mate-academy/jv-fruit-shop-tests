package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers
                = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                transaction -> {});
        shopService = new ShopServiceImpl(new OperationStrategyImpl(operationHandlers));
    }

    @Test
    public void process_nullTransactions_notOk() {
        assertThrows(RuntimeException.class, () -> {
            shopService.process(null);
        });
    }

    @Test
    public void process_emptyTransactions_notOk() {
        List<FruitTransaction> transactionList = new ArrayList<>();
        assertThrows(RuntimeException.class, () -> {
            shopService.process(transactionList);
        });
    }

    @Test
    public void process_normalTransactions_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        List<FruitTransaction> transactionList = List.of(fruitTransaction);
        assertDoesNotThrow(() -> shopService.process(transactionList));
    }
}
