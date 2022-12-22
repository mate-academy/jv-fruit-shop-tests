package core.basesyntax.strategy.filestrategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.InvalidFileTypeException;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.filestrategy.impl.CsvReportBuilderImpl;
import core.basesyntax.strategy.operationstrategy.OperationCalculator;
import core.basesyntax.strategy.operationstrategy.OperationStrategy;
import core.basesyntax.strategy.operationstrategy.impl.BalanceOperationCalculatorImpl;
import core.basesyntax.strategy.operationstrategy.impl.PurchaseOperationCalculatorImpl;
import core.basesyntax.strategy.operationstrategy.impl.ReturnOperationCalculatorImpl;
import core.basesyntax.strategy.operationstrategy.impl.SupplyOperationCalculatorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportBuilderStrategyTest {
    private static Map<String, ReportBuilder> reportBuilderMap;
    private static Map<Operation, OperationCalculator> operationCalculatorsMap;
    private static ReportBuilderStrategy reportBuilderStrategy;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        operationCalculatorsMap = new HashMap<>();
        operationCalculatorsMap.put(
                Operation.BALANCE, new BalanceOperationCalculatorImpl());
        operationCalculatorsMap.put(
                Operation.SUPPLY, new SupplyOperationCalculatorImpl());
        operationCalculatorsMap.put(
                Operation.PURCHASE, new PurchaseOperationCalculatorImpl());
        operationCalculatorsMap.put(
                Operation.RETURN, new ReturnOperationCalculatorImpl());
        operationStrategy = new OperationStrategy(operationCalculatorsMap);
        reportBuilderMap = new HashMap<>();
        reportBuilderMap.put(FileType.CSV.getName(), new CsvReportBuilderImpl(operationStrategy));
        reportBuilderStrategy = new ReportBuilderStrategy(reportBuilderMap);
    }

    @Test
    public void getReportBuilder_validCsvInput_ok() {
        Class<CsvReportBuilderImpl> expected = CsvReportBuilderImpl.class;
        Class<? extends ReportBuilder> actual
                = reportBuilderStrategy.getReportBuilder("csv").getClass();
        assertEquals("Should return instance of CsvReportBuilderImpl "
                + "when input is \"csv\", but was instance of " + actual, expected, actual);
    }

    @Test(expected = InvalidFileTypeException.class)
    public void getReportBuilder_invalidInput_notOk() {
        reportBuilderStrategy.getReportBuilder("invalid type");

    }

    @Test(expected = InvalidFileTypeException.class)
    public void getReportBuilder_nullInput_notOk() {
        reportBuilderStrategy.getReportBuilder(null);
    }
}
