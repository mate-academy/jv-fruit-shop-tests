package basesyntax;

import basesyntax.dao.StorageDao;
import basesyntax.dao.StorageDaoImpl;
import basesyntax.model.FruitTransaction;
import basesyntax.service.operation.OperationService;
import basesyntax.service.operation.OperationServiceImpl;
import basesyntax.service.parse.ParserService;
import basesyntax.service.parse.ParserServiceImpl;
import basesyntax.service.read.ReaderService;
import basesyntax.service.read.ReaderServiceImpl;
import basesyntax.service.report.ReportService;
import basesyntax.service.report.ReportServiceImpl;
import basesyntax.service.strategy.OperationStrategy;
import basesyntax.service.strategy.OperationStrategyImpl;
import basesyntax.service.strategy.handlers.BalanceOperationHandler;
import basesyntax.service.strategy.handlers.OperationHandler;
import basesyntax.service.strategy.handlers.PurchaseOperationHandler;
import basesyntax.service.strategy.handlers.ReturnOperationHandler;
import basesyntax.service.strategy.handlers.SupplyOperationHandler;
import basesyntax.service.write.WriterService;
import basesyntax.service.write.WriterServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        new WriterServiceImpl().write("input.csv","type,fruit,quantity\n"
                + "b,banana,18\n"
                + "b,apple,100\n"
                + "s,banana,14\n"
                + "p,banana,15\n"
                + "r,apple,100000\n"
                + "p,apple,50\n"
                + "p,banana,5\n"
                + "s,banana,544");
        doTask("input.csv", "report.csv");
    }

    private static void doTask(String fromFile, String toFile) {
        StorageDao storageDao = new StorageDaoImpl();
        ReaderService readerService = new ReaderServiceImpl();
        ParserService parserService = new ParserServiceImpl();
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new BalanceOperationHandler(storageDao));
        operationHandlerMap.put("p", new PurchaseOperationHandler(storageDao));
        operationHandlerMap.put("s", new SupplyOperationHandler(storageDao));
        operationHandlerMap.put("r", new ReturnOperationHandler(storageDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        OperationService operationService = new OperationServiceImpl(operationStrategy);
        ReportService reportService = new ReportServiceImpl();
        WriterService writerService = new WriterServiceImpl();
        List<String> data = readerService.readFile(fromFile);
        List<FruitTransaction> transactions = parserService.parse(data);
        operationService.process(transactions);
        String report = reportService.createReport(storageDao.getDataBase());
        writerService.write(toFile, report);
    }

}
