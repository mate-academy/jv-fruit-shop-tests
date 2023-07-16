package core.basesyntax;

import core.basesyntax.db.*;
import core.basesyntax.handler.*;
import core.basesyntax.handler.impl.*;
import core.basesyntax.model.*;
import core.basesyntax.service.*;
import core.basesyntax.service.impl.*;
import core.basesyntax.strategy.*;
import core.basesyntax.strategy.impl.*;
import core.basesyntax.utility.*;

import java.util.*;

public class Main {
    private static final String INPUT_CSV = "src/main/resources/input.csv";
    private static final String REPORT_CSV = "src/main/resources/report.csv";

    public static void main(String[] args) {
        Map<Operation, ShopOperationHandler> shopOperationHandlerMap = new HashMap<>();
        shopOperationHandlerMap.put(Operation.BALANCE,
                new BalanceOperationHandler());
        shopOperationHandlerMap.put(Operation.RETURN,
                new ReturnOperationHandler());
        shopOperationHandlerMap.put(Operation.SUPPLY,
                new SupplyOperationHandler());
        shopOperationHandlerMap.put(Operation.PURCHASE,
                new PurchaseOperationHandler());
        DataReaderService dataReaderService = new CsvReaderService();
        List<String> retrievedDataLines = dataReaderService.readData(INPUT_CSV);

        DataParserService dataParserService = new DataParserServiceImpl();
        List<FruitTransaction> transactionList = dataParserService.parseData(retrievedDataLines);

        ShopOperationStrategy operationStrategy =
                new ShopOperationStrategyImpl(shopOperationHandlerMap);
        DataProcessorService dataProcessorService =
                new FruitShopDataProcessorService(operationStrategy);
        dataProcessorService.processData(transactionList);

        new FruitQuantityChecker().checkFruitQuantity(Storage.fruitStorage);

        ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl();
        String reportData = reportCreatorService.createReport();

        DataWriterService csvWriter = new CsvWriterService();
        csvWriter.writeData(reportData, REPORT_CSV);
    }
}
