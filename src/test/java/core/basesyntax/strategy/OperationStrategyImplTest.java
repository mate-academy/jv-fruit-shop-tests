package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Storage.getFruits().clear();
        Map<String, Transaction> transactionMap = Map.of(
                "b", new BalanceTransaction(),
                "s", new SupplyTransaction(),
                "p", new PurchaseTransaction(),
                "r", new ReturnTransaction()
        );
        operationStrategy = new OperationStrategyImpl(transactionMap);
    }

    @Test
    void getOperationType_balanceInput_ok() {
        String input = "b";
        assertInstanceOf(BalanceTransaction.class,operationStrategy.getOperationType(input));
    }

    @Test
    void getOperationType_purchaseInput_ok() {
        String input = "p";
        assertInstanceOf(PurchaseTransaction.class,operationStrategy.getOperationType(input));
    }

    @Test
    void getOperationType_returnInput_ok() {
        String input = "r";
        assertInstanceOf(ReturnTransaction.class,operationStrategy.getOperationType(input));
    }

    @Test
    void getOperationType_supplyInput_ok() {
        String input = "s";
        assertInstanceOf(SupplyTransaction.class,operationStrategy.getOperationType(input));
    }

    @Test
    void getOperationType_incorrectInput_notOk() {
        String input = "x";
        assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getOperationType(input));
    }
}
