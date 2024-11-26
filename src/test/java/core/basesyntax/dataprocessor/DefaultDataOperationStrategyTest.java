package core.basesyntax.dataprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultDataOperationStrategyTest {

    private DefaultDataOperationStrategy strategy;

    @BeforeEach
    void setUp() {
        OperationHandler balanceHandler = (fruit, quantity) -> {};
        OperationHandler supplyHandler = (fruit, quantity) -> {};
        OperationHandler purchaseHandler = (fruit, quantity) -> {};
        OperationHandler returnHandler = (fruit, quantity) -> {};

        strategy = new DefaultDataOperationStrategy(
                Map.of(
                        Operation.BALANCE, balanceHandler,
                        Operation.SUPPLY, supplyHandler,
                        Operation.PURCHASE, purchaseHandler,
                        Operation.RETURN, returnHandler
                )
        );
    }

    @Test
    void getHandler_validOperation_returnsCorrectHandler() {
        OperationHandler balanceHandler = strategy.getHandler(Operation.BALANCE);
        assertEquals(balanceHandler, strategy.getHandler(Operation.BALANCE));
        OperationHandler supplyHandler = strategy.getHandler(Operation.SUPPLY);
        assertEquals(supplyHandler, strategy.getHandler(Operation.SUPPLY));
        OperationHandler purchaseHandler = strategy.getHandler(Operation.PURCHASE);
        assertEquals(purchaseHandler, strategy.getHandler(Operation.PURCHASE));
        OperationHandler returnHandler = strategy.getHandler(Operation.RETURN);
        assertEquals(returnHandler, strategy.getHandler(Operation.RETURN));
    }

    @Test
    void getHandler_invalidOperation_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> strategy.getHandler(Operation.UNKNOWN));
    }

    @Test
    void getHandler_missingHandler_throwsIllegalArgumentException() {
        DefaultDataOperationStrategy incompleteStrategy = new DefaultDataOperationStrategy(
                Map.of(
                        Operation.BALANCE, (fruit, quantity) -> {}
                )
        );
        assertThrows(IllegalArgumentException.class, () -> incompleteStrategy.getHandler(
                Operation.SUPPLY
        ));
    }
}
