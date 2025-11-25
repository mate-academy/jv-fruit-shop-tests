package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.CalculateAvailableFruits;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.provider.StrategyProvider;
import core.basesyntax.service.ReportService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseStrategy;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyStrategy;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private final Map<Operation, OperationStrategy> strategyMap = Map.of(
            Operation.RETURN, new ReturnOperation(),
            Operation.PURCHASE, new PurchaseStrategy(),
            Operation.BALANCE, new BalanceOperation(),
            Operation.SUPPLY, new SupplyStrategy()
    );
    private final StrategyProvider strategyProvider = new StrategyProvider(strategyMap);
    private final ReportService reportService = new ReportServiceImpl(
            new CalculateAvailableFruits(strategyProvider)
    );

    @Test
    public void createReport_validData_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.SUPPLY, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 13),
                new FruitTransaction(Operation.PURCHASE, "banana", 5),
                new FruitTransaction(Operation.SUPPLY, "banana", 50)
        );

        String expected = """
                    fruit,quantity
                    banana,152
                    """.trim();

        String actual = this.reportService.createReport(transactions);

        assertEquals(expected, actual);
    }

    @Test
    public void createReport_nullData_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.reportService.createReport(null)
        );
    }

    @Test
    public void createReport_invalidQuantityOfFruits_notOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.SUPPLY, "banana", 10),
                new FruitTransaction(Operation.RETURN, "banana", 5),
                new FruitTransaction(Operation.PURCHASE, "banana", 40)
        );

        assertThrows(
                RuntimeException.class,
                () -> this.reportService.createReport(transactions)
        );
    }
}
