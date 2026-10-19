package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.FruitTransaction;
import core.basesyntax.FruitTransaction.Operation;
import core.basesyntax.operation.handler.OperationHandler;
import core.basesyntax.operation.operationstrategy.OperationStrategy;
import core.basesyntax.shopservice.ShopServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ShopServiceImplTest {
    private List<String> callLog;
    private ShopServiceImpl shopService;

    @Before
    public void setUp() {
        callLog = new ArrayList<>();

        OperationStrategy strategy = new OperationStrategy() {
            private final Map<Operation, OperationHandler> handlers = new HashMap<>();

            {
                handlers.put(Operation.BALANCE, (fruit, quantity) ->
                        callLog.add("BALANCE:" + fruit + ":" + quantity));
                handlers.put(Operation.SUPPLY, (fruit, quantity) ->
                        callLog.add("SUPPLY:" + fruit + ":" + quantity));
            }

            @Override
            public OperationHandler getHandler(Operation operation) {
                return handlers.get(operation);
            }
        };

        shopService = new ShopServiceImpl(strategy);
    }

    @Test
    public void process_multipleTransactions_callsCorrectHandlers() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.BALANCE, "banana", 10));
        transactions.add(new FruitTransaction(Operation.SUPPLY, "apple", 20));

        shopService.process(transactions);

        assertEquals(2, callLog.size());
        assertEquals("BALANCE:banana:10", callLog.get(0));
        assertEquals("SUPPLY:apple:20", callLog.get(1));
    }

    @Test
    public void process_emptyList_noHandlersCalled() {
        shopService.process(new ArrayList<>());

        assertEquals(0, callLog.size());
    }
}
