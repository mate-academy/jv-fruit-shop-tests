package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.StrategyService;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StrategyServiceImplTest {
    @AfterEach
    void clear() {
        Storage.storage.clear();
    }

    @Test
    void when_OperationExists_Ok() {
        Map<Operation, OperationHandler> operationHandlerMap = Map.of(
                Operation.BALANCE,
                new BalanceOperationHandler()
        );
        StrategyService strategyService = new StrategyServiceImpl(operationHandlerMap);
        List<FruitTransaction> transactionList = List.of(
                new FruitTransaction(
                        Operation.BALANCE,
                        "apple",
                        100
                )
        );
        strategyService.processTransactions(transactionList);
        Map<String, Integer> expected = Map.of("apple", 100);
        assertEquals(expected, Storage.storage);
    }
}
