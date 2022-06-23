package core.basesyntax;

import core.basesyntax.servise.FileReaderService;
import core.basesyntax.servise.FileWriterService;
import core.basesyntax.servise.ReportService;
import core.basesyntax.servise.TransactionService;
import core.basesyntax.servise.impl.FileReaderServiceImpl;
import core.basesyntax.servise.impl.FileWriterServiceImpl;
import core.basesyntax.servise.impl.ReportServiceImpl;
import core.basesyntax.servise.impl.TransactionServiceImpl;
import core.basesyntax.servise.transaction.TransactionHandler;
import core.basesyntax.servise.transaction.impl.BalanceTransactionHandler;
import core.basesyntax.servise.transaction.impl.PurchaseTransactionHandler;
import core.basesyntax.servise.transaction.impl.ReturnTransactionHandler;
import core.basesyntax.servise.transaction.impl.SupplyTransactionHandler;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.TransactionStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String DAILY_RECORDS_FILEPATH = "src/resources/daily_records.csv";
    private static final String DAILY_REPORT_FILEPATH = "src/resources/daily_report.csv";

    public static void main(String[] args) {
        Map<String, TransactionHandler> transactionHandlers = new HashMap<>();
        transactionHandlers.put("b", new BalanceTransactionHandler());
        transactionHandlers.put("s", new SupplyTransactionHandler());
        transactionHandlers.put("p", new PurchaseTransactionHandler());
        transactionHandlers.put("r", new ReturnTransactionHandler());
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl(transactionHandlers);

        FileReaderService fileReaderService = new FileReaderServiceImpl();
        List<String> records = fileReaderService.readFile(DAILY_RECORDS_FILEPATH);

        TransactionService transactionService = new TransactionServiceImpl(transactionStrategy);
        transactionService.process(records);

        ReportService reportService = new ReportServiceImpl();
        String report = reportService.createReport();

        FileWriterService fileWriterService = new FileWriterServiceImpl();
        fileWriterService.writeStringToFile(DAILY_REPORT_FILEPATH, report);
    }
}
