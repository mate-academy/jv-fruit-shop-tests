package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.record.Operation;
import core.basesyntax.service.impl.BalanceOperation;
import core.basesyntax.service.impl.PurchaseOperation;
import core.basesyntax.service.impl.ReturnOperation;
import core.basesyntax.service.impl.SupplyOperation;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new OperationStrategyImpl(Map
                .of(Operation.BALANCE, new BalanceOperation(),
                        Operation.RETURN, new ReturnOperation(),
                        Operation.PURCHASE, new PurchaseOperation(),
                        Operation.SUPPLY, new SupplyOperation()));
    }

    @Test
    void get_ByOperationTypeBalance_ok() {
        var expected = BalanceOperation.class;
        var actual = strategy.get(Operation.BALANCE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    void get_ByOperationTypeReturn_ok() {
        var expected = ReturnOperation.class;
        var actual = strategy.get(Operation.RETURN).getClass();
        assertEquals(expected, actual);
    }

    @Test
    void get_ByOperationTypePurchase_ok() {
        var expected = PurchaseOperation.class;
        var actual = strategy.get(Operation.PURCHASE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    void get_ByOperationTypeSupply_ok() {
        var expected = SupplyOperation.class;
        var actual = strategy.get(Operation.SUPPLY).getClass();
        assertEquals(expected, actual);
    }

    @Test
    void get_fromEmptyConstructor_ok() {
        var actual = new OperationStrategyImpl().get(Operation.BALANCE).getClass();
        var expected = strategy.get(Operation.BALANCE).getClass();
        assertEquals(expected, actual);
    }
}
