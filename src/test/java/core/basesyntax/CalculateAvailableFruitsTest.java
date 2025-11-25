package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.provider.StrategyProvider;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseStrategy;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculateAvailableFruitsTest {
    private ReportGenerator calculateAvailableFruits;

    @BeforeEach
    public void setUp() {
        Map<Operation, OperationStrategy> strategies = new HashMap<>();
        strategies.put(Operation.RETURN, new ReturnOperation());
        strategies.put(Operation.PURCHASE, new PurchaseStrategy());
        strategies.put(Operation.BALANCE, new BalanceOperation());
        strategies.put(Operation.SUPPLY, new SupplyStrategy());
        StrategyProvider strategyProvider = new StrategyProvider(strategies);
        this.calculateAvailableFruits = new CalculateAvailableFruits(strategyProvider);
    }

    @Test
    public void generate_nullInput_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.calculateAvailableFruits.generate(null)
        );
    }

    @Test
    public void generate_newQtyIsNegative_notOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 15),
                new FruitTransaction(Operation.BALANCE, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 12),
                new FruitTransaction(Operation.RETURN, "apple", 1),
                new FruitTransaction(Operation.SUPPLY, "apple", 3)
        );

        assertThrows(
                RuntimeException.class,
                () -> this.calculateAvailableFruits.generate(transactions)
        );
    }

    @Test
    public void generate_newQtyIsEquals0_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 15),
                new FruitTransaction(Operation.BALANCE, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 10),
                new FruitTransaction(Operation.RETURN, "apple", 1),
                new FruitTransaction(Operation.SUPPLY, "apple", 3)
        );

        assertDoesNotThrow(
                () -> this.calculateAvailableFruits.generate(transactions)
        );
    }

    @Test
    public void generate_validInput_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 15),
                new FruitTransaction(Operation.BALANCE, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 9),
                new FruitTransaction(Operation.RETURN, "apple", 1),
                new FruitTransaction(Operation.SUPPLY, "apple", 3)
        );

        assertDoesNotThrow(
                () -> this.calculateAvailableFruits.generate(transactions)
        );
    }
}
