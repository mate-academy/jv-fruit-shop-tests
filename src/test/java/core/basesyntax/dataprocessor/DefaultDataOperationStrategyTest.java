package core.basesyntax.dataprocessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultDataOperationStrategyTest {
    private DefaultDataOperationStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new DefaultDataOperationStrategy(
                Map.of(
                        Operation.BALANCE, new BalanceHandler(),
                        Operation.SUPPLY, new SupplyHandler(),
                        Operation.PURCHASE, new PurchaseHandler(),
                        Operation.RETURN, new ReturnHandler()
                )
        );
    }

    @Test
    void getHandler_validOperation_returnsCorrectHandler() {
        assertTrue(strategy.getHandler(Operation.BALANCE) instanceof BalanceHandler);
        assertTrue(strategy.getHandler(Operation.SUPPLY) instanceof SupplyHandler);
        assertTrue(strategy.getHandler(Operation.PURCHASE) instanceof PurchaseHandler);
        assertTrue(strategy.getHandler(Operation.RETURN) instanceof ReturnHandler);
    }

    @Test
    void getHandler_invalidOperation_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> strategy.getHandler(null)
        );
        assertEquals("Invalid operation provided", exception.getMessage());
    }

    @Test
    void getHandler_nullOperation_throwsIllegalArgumentException() {
        DefaultDataOperationStrategy strategy = new DefaultDataOperationStrategy(
                Map.of(Operation.BALANCE, (fruit, quantity) -> {})
        );
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> strategy.getHandler(null)
        );
        assertEquals("Invalid operation provided", exception.getMessage());
    }

    @Test
    void getHandler_missingHandler_throwsIllegalArgumentException() {
        DefaultDataOperationStrategy incompleteStrategy = new DefaultDataOperationStrategy(
                Map.of(
                        Operation.BALANCE, (fruit, quantity) -> {}
                )
        );
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> incompleteStrategy.getHandler(Operation.SUPPLY)
        );
        assertEquals("Handler not found for operation: SUPPLY", exception.getMessage());
    }
}
