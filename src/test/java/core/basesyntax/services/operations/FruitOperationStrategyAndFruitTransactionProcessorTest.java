package core.basesyntax.services.operations;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.exceptions.FruitStorageException;
import core.basesyntax.services.FruitTransactionProcessor;
import core.basesyntax.services.impl.FruitTransactionProcessorImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitOperationStrategyAndFruitTransactionProcessorTest {

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
    void getHandlerReturnsCorrectHandlerForBalanceOperationOk() {
        FruitTransactionDto dto = new FruitTransactionDto("b", "Apple", 10);
        List<OperationHandler> result = strategy.getHandler(dto);
        assertEquals(1, result.size(), "Expected one handler");
        assertTrue(result.get(0) instanceof BalanceOperationHandler,
                "Expected BalanceOperationHandler");
    }

    @Test
    void applyThrowsExceptionWhenFruitNotInStorageNotOk() {
        PurchaseOperationHandler handler = new PurchaseOperationHandler();
        FruitTransactionDto dto = new FruitTransactionDto("p", "Apple", 10);

        assertThrows(FruitStorageException.class, () -> handler.apply(dto),
                "Expected FruitStorageException when fruit is not in storage");
    }

    @Test
    void applyThrowsExceptionWhenPurchaseAmountIsTooBigNotOk() {
        PurchaseOperationHandler handler = new PurchaseOperationHandler();
        FruitTransactionDto dto = new FruitTransactionDto("p", "Apple", 20);
        assertThrows(FruitStorageException.class, () -> handler.apply(dto),
                "Expected FruitStorageException when purchase amount is too big");
    }

    @Test
    void applyReducesQuantityWhenPurchaseAmountIsValidOk() {
        PurchaseOperationHandler handler = new PurchaseOperationHandler();
        FruitTransactionDto dto = new FruitTransactionDto("p", "Apple", 5);
        fruitStorage.put("Apple", 10);
        assertDoesNotThrow(() -> handler.apply(dto),
                "Expected no exception when purchase amount is valid");
        assertEquals(5, fruitStorage.get("Apple"), "Expected 5 apples left in storage");
    }

    @Test
    void getHandlerReturnsCorrectHandlerForReturnOperationOk() {
        FruitTransactionDto dto = new FruitTransactionDto("r", "Apple", 10);
        List<OperationHandler> result = strategy.getHandler(dto);
        assertEquals(1, result.size(), "Expected one handler");
        assertTrue(result.get(0) instanceof ReturnOperationHandler,
                "Expected ReturnOperationHandler");
    }

    @Test
    void getHandlerReturnsCorrectHandlerForSupplyOperationOk() {
        FruitTransactionDto dto = new FruitTransactionDto("s", "Apple", 10);
        List<OperationHandler> result = strategy.getHandler(dto);
        assertEquals(1, result.size(), "Expected one handler");
        assertTrue(result.get(0) instanceof SupplyOperationHandler,
                "Expected SupplyOperationHandler");
    }

    @Test
    void processCallsHandlersForEachDtoOk() {
        processor = new FruitTransactionProcessorImpl(strategy);
        FruitTransactionDto dto1 = new FruitTransactionDto("b", "Apple", 10);
        FruitTransactionDto dto2 = new FruitTransactionDto("s", "Banana", 20);
        List<FruitTransactionDto> dtos = List.of(dto1, dto2);
        assertDoesNotThrow(() -> processor.process(dtos), "Expected no exception");
    }
}
