package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReadFile;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.ReportWriter;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.TransactionStrategy;
import core.basesyntax.service.calculation.BalanceCalculator;
import core.basesyntax.service.calculation.PurchaseCalculator;
import core.basesyntax.service.calculation.ReturnCalculator;
import core.basesyntax.service.calculation.SupplyCalculator;
import core.basesyntax.service.calculation.TransactionCalculation;
import core.basesyntax.service.impl.ReadFileImpl;
import core.basesyntax.service.impl.ReportCreatorImpl;
import core.basesyntax.service.impl.ReportWriterImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import core.basesyntax.service.impl.TransactionStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String PATH_NAME_DB = "src/main/java/core/basesyntax/db/database.csv";
    private static String PATH_NAME_REPORT = "src/main/java/core/basesyntax/report/report.csv";

    public static void main(String[] args) {
        Map<String, TransactionCalculation> strategyMap = new HashMap<>();
        strategyMap.put(FruitTransaction.Operation.BALANCE.getOperations(),
                new BalanceCalculator());
        strategyMap.put(FruitTransaction.Operation.SUPPLY.getOperations(),
                new SupplyCalculator());
        strategyMap.put(FruitTransaction.Operation.PURCHASE.getOperations(),
                new PurchaseCalculator());
        strategyMap.put(FruitTransaction.Operation.RETURN.getOperations(),
                new ReturnCalculator());

        ReadFile readFile = new ReadFileImpl();
        TransactionParser parseFile = new TransactionParserImpl();
        List<FruitTransaction> fruitTransactions = parseFile
                .parseFile(readFile.readFile(PATH_NAME_DB));
        TransactionStrategy strategy = new TransactionStrategyImpl(strategyMap);
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            strategy.get(fruitTransaction.getOperation()).calculate(fruitTransaction);
        }
        ReportCreator createReport = new ReportCreatorImpl();
        String report = createReport.createReport();
        ReportWriter writeReport = new ReportWriterImpl();
        writeReport.reportWriter(PATH_NAME_REPORT, report);
    }
}
