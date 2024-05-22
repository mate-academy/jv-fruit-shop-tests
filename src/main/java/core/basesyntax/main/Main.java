package core.basesyntax.main;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.service.TransactionParserService;
import core.basesyntax.service.impl.BalanceReportCreatorService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.TransactionParserServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.operation.ReturnOperationHandler;
import core.basesyntax.strategy.operation.SupplyOperationHandler;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String STATISTIC_FILE_PATH = "src/main/resources/"
            + "balance.csv";
    private static final String DATA_FILE_PATH = "src/main/resources/dataBase.csv";

    public static void main(String[] args) {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap =
                Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                        FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
                        FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
                        FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        TransactionParserService parser = new TransactionParserServiceImpl();
        FruitShopDao dao = new FruitShopDaoImpl();
        OperationStrategy strategy = new OperationStrategyImpl(operationHandlerMap);
        ReportCreatorService reportCreator = new BalanceReportCreatorService();
        FileWriterService fileWriter = new FileWriterServiceImpl();
        List<String> dataFromFile = fileReaderService.read(DATA_FILE_PATH);
        List<FruitTransaction> allFruitTransaction = parser.parseFromListStrings(dataFromFile);
        for (FruitTransaction fruitTransaction : allFruitTransaction) {
            strategy.getHandler(fruitTransaction).execute(fruitTransaction);
        }
        List<String> balanceReport = reportCreator.createReport();
        fileWriter.write(balanceReport, STATISTIC_FILE_PATH);
    }
}
