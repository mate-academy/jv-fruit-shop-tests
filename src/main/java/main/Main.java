package main;

import core.basesyntax.data.dao.FileReader;
import core.basesyntax.data.dao.FileReaderImpl;
import core.basesyntax.data.dao.FileWriter;
import core.basesyntax.data.dao.FileWriterImpl;
import core.basesyntax.data.model.FruitTransaction;
import core.basesyntax.data.model.OperationType;
import core.basesyntax.data.servise.DataConverter;
import core.basesyntax.data.servise.ReportGenerator;
import core.basesyntax.data.servise.ShopService;
import core.basesyntax.data.servise.StrategyOperationService;
import core.basesyntax.data.servise.ipl.DataConverterImpl;
import core.basesyntax.data.servise.ipl.ReportGeneratorImpl;
import core.basesyntax.data.servise.ipl.ShopServiceImpl;
import core.basesyntax.data.servise.ipl.StrategyOperationServiceImpl;
import core.basesyntax.data.strategy.BalanceOperationHandler;
import core.basesyntax.data.strategy.OperationHandler;
import core.basesyntax.data.strategy.PurchaseOperationHandler;
import core.basesyntax.data.strategy.ReturnOperationHandler;
import core.basesyntax.data.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String SOURCE_PATH_NAME = "src/resources/fruit_shop_transactions";
    private static final String TARGET_PATH_NAME = "src/resources/report";

    public static void main(String[] args) {
        FileReader fileReader = new FileReaderImpl();

        List<String> inputReport = fileReader.readFile(SOURCE_PATH_NAME);

        DataConverter dataConverter = new DataConverterImpl();

        Map<OperationType, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(OperationType.BALANCE, new BalanceOperationHandler());
        operationHandlers.put(OperationType.SUPPLY, new SupplyOperationHandler());
        operationHandlers.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        operationHandlers.put(OperationType.RETURN, new ReturnOperationHandler());

        StrategyOperationService operationStrategy = new StrategyOperationServiceImpl(
                operationHandlers);

        List<FruitTransaction> operations = dataConverter.convertToOperation(inputReport);

        ShopService fruitShopService = new ShopServiceImpl(operationStrategy);
        fruitShopService.process(operations);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String resultingReport = reportGenerator.getReport();

        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeFile(TARGET_PATH_NAME, resultingReport);

        System.out.println("Report was successfully written in:  " + TARGET_PATH_NAME);
    }
}
