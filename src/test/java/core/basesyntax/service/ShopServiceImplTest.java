package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShopServiceImplTest {

    @Test
    void process_validTransactions_ok() {
        FakeOperationHandler handler = new FakeOperationHandler();
        FakeOperationStrategy strategy = new FakeOperationStrategy(handler);

        ShopService shopService = new ShopServiceImpl(strategy);

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "apple", 10)
        );

        shopService.process(transactions);

        assertEquals(2, handler.callCount);
    }

    private static class FakeOperationStrategy implements OperationStrategy {
        private final OperationHandler handler;

        public FakeOperationStrategy(OperationHandler handler) {
            this.handler = handler;
        }

        @Override
        public OperationHandler get(FruitTransaction.Operation operation) {
            return handler;
        }
    }

    private static class FakeOperationHandler implements OperationHandler {
        private int callCount = 0;

        @Override
        public void apply(FruitTransaction transaction) {
            callCount++;
        }
    }
}
