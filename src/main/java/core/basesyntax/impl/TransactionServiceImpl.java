package core.basesyntax.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.ReadService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.WriteService;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private OperationStrategy operationStrategy;
    private ReadService readService;
    private ParseService parseService;
    private ReportService reportService;
    private WriteService writeService;

    public TransactionServiceImpl(OperationStrategy operationStrategy, ReadService readService,
                                  ParseService parseService, ReportService reportService,
                                  WriteService writeService) {
        this.operationStrategy = operationStrategy;
        this.readService = readService;
        this.parseService = parseService;
        this.reportService = reportService;
        this.writeService = writeService;
    }

    @Override
    public void processTransactions() {
        List<String> dataFromFile = readService.read("src/main/java/database.csv");
        List<FruitTransaction> fruitTransactions = parseService.parse(dataFromFile);

        for (FruitTransaction fruitTransaction : fruitTransactions) {
            OperationHandler operationHandler =
                    operationStrategy.get(fruitTransaction.getOperation());
            operationHandler.operate(fruitTransaction);
        }

        String report = reportService.createReport();
        writeService.writeToFile("src/main/java/report.csv", report);
    }
}
