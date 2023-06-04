package core.basesyntax.main;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.OutFileStructure;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.ParserService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//
public class Main {
    private static final String INPUT_FILE_NAME = "src/main/resources/activities.csv";
    private static final String OUTPUT_FILE_NAME = "src/main/resources/dailyreport.csv";
    private static final String FIRST_REPORT_COLUMN = "fruit";
    private static final String SECOND_REPORT_COLUMN = "quantity";

    public static void main(String[] arg) {
        ReaderServiceImpl readFromCsvFile = new ReaderServiceImpl();
        List<String> dataFromFile = readFromCsvFile.readFromCsvFile(INPUT_FILE_NAME);

        Map<OperationType, OperationHandler> operationTypeMap = new HashMap<>();

        OperationHandler balanceOperationHandler = new BalanceOperationHandlerImpl();
        OperationHandler purchaseOperationHandler = new PurchaseOperationHandlerImpl();
        OperationHandler supplyOperationHandler = new SupplyOperationHandlerImpl();
        OperationHandler returnOperationHandler = new ReturnOperationHandlerImpl();

        operationTypeMap.put(OperationType.BALANCE, balanceOperationHandler);
        operationTypeMap.put(OperationType.SUPPLY, supplyOperationHandler);
        operationTypeMap.put(OperationType.RETURN, returnOperationHandler);
        operationTypeMap.put(OperationType.PURCHASE, purchaseOperationHandler);

        OperationStrategy operationStrategy =
                new OperationStrategyImpl(operationTypeMap);

        ParserService parserService = new ParserServiceImpl();
        List<FruitTransaction> fruitsTransactionList = parserService
                .getFruitTransaction(dataFromFile);

        FruitShopService fruitShopService =
                new FruitShopServiceImpl(operationStrategy);
        fruitShopService.getFruitBalance(fruitsTransactionList);

        OutFileStructure outFileStructure =
                new OutFileStructure(FIRST_REPORT_COLUMN, SECOND_REPORT_COLUMN);

        FruitsStorage fruitCurrentStorage = new FruitsStorage();

        ReportService reportService = new ReportServiceImpl();
        String dataReport = reportService.getDataReport(outFileStructure, fruitCurrentStorage);

        WriterService writer = new WriterServiceImpl();
        writer.writeToCsvFile(OUTPUT_FILE_NAME, dataReport);

        System.out.println(fruitCurrentStorage.getFruitsStorage().toString());
    }
}
