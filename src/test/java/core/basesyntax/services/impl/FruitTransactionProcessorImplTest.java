package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.services.FruitTransactionProcessor;
import core.basesyntax.services.operations.BalanceOperationHandler;
import core.basesyntax.services.operations.FruitOperationStrategy;
import core.basesyntax.services.operations.OperationHandler;
import core.basesyntax.services.operations.PurchaseOperationHandler;
import core.basesyntax.services.operations.ReturnOperationHandler;
import core.basesyntax.services.operations.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionProcessorImplTest {

    private static FruitTransactionProcessor processor;
    private static List<OperationHandler> handlers;
    private static FruitOperationStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        handlers = new ArrayList<>();
        handlers.add(new BalanceOperationHandler());
        handlers.add(new PurchaseOperationHandler());
        handlers.add(new ReturnOperationHandler());
        handlers.add(new SupplyOperationHandler());
        strategy = new FruitOperationStrategy(handlers);
        processor = new FruitTransactionProcessorImpl(strategy);
    }

    @Test
    void process_CallsHandlersForEachDto_Ok() {
        FruitTransactionDto dto1 = new FruitTransactionDto("b", "Apple", 10);
        FruitTransactionDto dto2 = new FruitTransactionDto("s", "Banana", 20);
        List<FruitTransactionDto> dtos = List.of(dto1, dto2);
        assertDoesNotThrow(() -> processor.process(dtos), "Expected no exception");
    }
}
