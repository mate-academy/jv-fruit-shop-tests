package core.basesyntax;

import core.basesyntax.model.Transaction;
import core.basesyntax.servise.FileReaderService;
import core.basesyntax.servise.FileWriterService;
import core.basesyntax.servise.ReportService;
import core.basesyntax.servise.TransactionService;
import core.basesyntax.servise.TransactionStrategy;
import core.basesyntax.servise.impl.FileReaderServiceImpl;
import core.basesyntax.servise.impl.FileWriterServiceImpl;
import core.basesyntax.servise.impl.ReportServiceImpl;
import core.basesyntax.servise.impl.TransactionServiceImpl;
import core.basesyntax.servise.impl.TransactionStrategyImpl;
import java.util.List;

public class Main {
    private static final String DAILY_RECORDS_FILEPATH = "src/resources/daily_records.csv";
    private static final String DAILY_REPORT_FILEPATH = "src/resources/daily_report.csv";

    public static void main(String[] args) {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        List<String> records = fileReaderService.read(DAILY_RECORDS_FILEPATH);

        TransactionService transactionService = new TransactionServiceImpl();
        List<Transaction> transactions = transactionService.processData(records);

        TransactionStrategy transactionStrategy = new TransactionStrategyImpl();
        transactions.forEach(t -> transactionStrategy
                .get(t.getOperation().getType()).proceedTransaction(t.getItem(), t.getQuantity()));

        ReportService reportService = new ReportServiceImpl();
        String report = reportService.createReport();

        FileWriterService fileWriterService = new FileWriterServiceImpl();
        fileWriterService.writeStringToFile(DAILY_REPORT_FILEPATH, report);
    }
}
