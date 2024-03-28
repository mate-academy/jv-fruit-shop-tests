package core.basesyntax.services.operations;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.services.FruitTransactionProcessor;
import core.basesyntax.services.impl.FruitTransactionProcessorImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitOperationStrategyTest {

    private static FruitOperationStrategy strategy;
    private static List<OperationHandler> handlers;
    private FruitTransactionProcessor processor;

    @BeforeAll
    static void beforeAll() {
        handlers = new ArrayList<>();
        handlers.add(new BalanceOperationHandler());
        handlers.add(new PurchaseOperationHandler());
        handlers.add(new ReturnOperationHandler());
        handlers.add(new SupplyOperationHandler());
        strategy = new FruitOperationStrategy(handlers);
    }

    @AfterEach
    public void tearDown() {
        fruitStorage.clear();
    }

    @Test
    void getHandler_ReturnsCorrectHandler_ForBalanceOperation_Ok() {
        FruitTransactionDto dto = new FruitTransactionDto("b", "Apple", 10);
        List<OperationHandler> result = strategy.getHandler(dto);
        assertEquals(1, result.size(), "Expected one handler");
        assertTrue(result.get(0) instanceof BalanceOperationHandler,
                "Expected BalanceOperationHandler");
    }

    @Test
    void getHandler_ReturnsCorrectHandler_ForReturnOperation_Ok() {
        FruitTransactionDto dto = new FruitTransactionDto("r", "Apple", 10);
        List<OperationHandler> result = strategy.getHandler(dto);
        assertEquals(1, result.size(), "Expected one handler");
        assertTrue(result.get(0) instanceof ReturnOperationHandler,
                "Expected ReturnOperationHandler");
    }

    @Test
    void getHandler_ReturnsCorrectHandler_ForSupplyOperation_Ok() {
        FruitTransactionDto dto = new FruitTransactionDto("s", "Apple", 10);
        List<OperationHandler> result = strategy.getHandler(dto);
        assertEquals(1, result.size(), "Expected one handler");
        assertTrue(result.get(0) instanceof SupplyOperationHandler,
                "Expected SupplyOperationHandler");
    }

    @Test
    void process_CallsHandlers_ForEachDto_Ok() {
        processor = new FruitTransactionProcessorImpl(strategy);
        FruitTransactionDto dto1 = new FruitTransactionDto("b", "Apple", 10);
        FruitTransactionDto dto2 = new FruitTransactionDto("s", "Banana", 20);
        List<FruitTransactionDto> dtos = List.of(dto1, dto2);
        assertDoesNotThrow(() -> processor.process(dtos), "Expected no exception");
    }
}
