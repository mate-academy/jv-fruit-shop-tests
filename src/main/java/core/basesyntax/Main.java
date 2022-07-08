package core.basesyntax;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileReaderServiceImpl;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.FileWriterServiceImpl;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.ReportServiceImpl;
import core.basesyntax.service.TransactionProcessorService;
import core.basesyntax.service.TransactionProcessorServiceImpl;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.TransactionStrategyImpl;
import core.basesyntax.transaction.BalanceTransactionHandler;
import core.basesyntax.transaction.PurchaseTransactionHandler;
import core.basesyntax.transaction.ReturnTransactionHandler;
import core.basesyntax.transaction.SupplyTransactionHandler;
import core.basesyntax.transaction.TransactionHandler;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, TransactionHandler> transactionHandlersMap = new HashMap<>();
        transactionHandlersMap.put("b", new BalanceTransactionHandler());
        transactionHandlersMap.put("s", new SupplyTransactionHandler());
        transactionHandlersMap.put("p", new PurchaseTransactionHandler());
        transactionHandlersMap.put("r", new ReturnTransactionHandler());

        FruitDao dao = new FruitDaoImpl();
        TransactionStrategy strategy = new TransactionStrategyImpl(transactionHandlersMap);

        FileReaderService readerService = new FileReaderServiceImpl();
        TransactionProcessorService processorService = new TransactionProcessorServiceImpl(dao,
                strategy);
        ReportService reportService = new ReportServiceImpl(dao);
        FileWriterService writerService = new FileWriterServiceImpl();

        processorService.process(readerService.readFile("src/main/resources/file.csv"));
        writerService.writeFile(reportService.makeReport(), "src/main/resources/report.csv");
    }
}
