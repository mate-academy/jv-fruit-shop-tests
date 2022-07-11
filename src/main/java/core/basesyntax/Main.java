package core.basesyntax;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.impl.ProcessorServiceImpl;
import core.basesyntax.impl.ReaderServiceImpl;
import core.basesyntax.impl.ReportServiceImpl;
import core.basesyntax.impl.WriterServiceImpl;
import core.basesyntax.service.ProcessorService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
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

        ReaderService readerService = new ReaderServiceImpl();
        ProcessorService processorService = new ProcessorServiceImpl(dao, strategy);
        ReportService reportService = new ReportServiceImpl(dao);
        WriterService writerService = new WriterServiceImpl();

        processorService.processData(readerService.readFile("src/main/resources/file.csv"));
        writerService.writeFile(reportService.makeReport(), "src/main/resources/report.csv");
    }
}
