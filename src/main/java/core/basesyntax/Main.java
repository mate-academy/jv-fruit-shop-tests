package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.DataConverterService;
import core.basesyntax.services.FileReaderService;
import core.basesyntax.services.ReportGenerator;
import core.basesyntax.services.ReportWriter;
import core.basesyntax.services.ShopService;
import core.basesyntax.services.impl.DataConverterServiceImpl;
import core.basesyntax.services.impl.FileReaderServiceImpl;
import core.basesyntax.services.impl.ReportGeneratorImpl;
import core.basesyntax.services.impl.ReportWriterImpl;
import core.basesyntax.services.impl.ShopServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.io.File;
import java.util.List;
import java.util.Map;

public class Main {
    private static final File REPORT
            = new File("src/main/resources/reports/report.csv");
    private static final File FINAL_REPORT
            = new File("src/main/resources/reports/final-report.csv");

    public static void main(String[] args) {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        List<String> inputReport = fileReaderService.read(REPORT);

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        DataConverterService dataConverter = new DataConverterServiceImpl();
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);

        ShopService shopService = new ShopServiceImpl(operationStrategy);
        shopService.process(transactions);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String resultingReport = reportGenerator.getReport();

        ReportWriter fileWriter = new ReportWriterImpl();
        fileWriter.write(resultingReport, FINAL_REPORT);
    }
}
