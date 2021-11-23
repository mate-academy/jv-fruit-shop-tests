package core.basesyntax.fruitshop;

import core.basesyntax.fruitshop.model.OperationType;
import core.basesyntax.fruitshop.model.RecordDto;
import core.basesyntax.fruitshop.parser.DtoCreator;
import core.basesyntax.fruitshop.parser.DtoCreatorImpl;
import core.basesyntax.fruitshop.service.FruitShopService;
import core.basesyntax.fruitshop.service.FruitShopServiceImpl;
import core.basesyntax.fruitshop.service.InputDataReader;
import core.basesyntax.fruitshop.service.InputDataReaderImpl;
import core.basesyntax.fruitshop.service.OperationStrategy;
import core.basesyntax.fruitshop.service.OperationStrategyImpl;
import core.basesyntax.fruitshop.service.operations.BalanceOperationHandler;
import core.basesyntax.fruitshop.service.operations.OperationHandler;
import core.basesyntax.fruitshop.service.operations.PurchaseOperationHandler;
import core.basesyntax.fruitshop.service.operations.ReturnOperationHandler;
import core.basesyntax.fruitshop.service.operations.SupplyOperationHandler;
import core.basesyntax.fruitshop.service.reporthandlers.FileWriter;
import core.basesyntax.fruitshop.service.reporthandlers.FileWriterImpl;
import core.basesyntax.fruitshop.service.reporthandlers.ReportGenerator;
import core.basesyntax.fruitshop.service.reporthandlers.ReportGeneratorImpl;
import core.basesyntax.fruitshop.service.validators.OperationsValidator;
import core.basesyntax.fruitshop.service.validators.OperationsValidatorImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String REPORT_FILE_NAME = "ReportForFruitShop";
    private static final String INPUT_FILE_PATH = "src/main/resources/InputDataForFruitShop";

    public static void main(String[] args) {
        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(OperationType.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(OperationType.SUPPLY, new SupplyOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        InputDataReader operationReader = new InputDataReaderImpl();
        List<String> rawDataFromFile = operationReader
                .readFromFile(INPUT_FILE_PATH);
        OperationsValidator inputDataValidator = new OperationsValidatorImpl();
        List<String> validFruitShopData = inputDataValidator.validator(rawDataFromFile);
        DtoCreator dataParser = new DtoCreatorImpl();
        List<RecordDto> operationsDataInDto = dataParser.toDtoDataFormatter(validFruitShopData);
        FruitShopService fruitShopService = new FruitShopServiceImpl(operationStrategy);
        fruitShopService.fruitStorageModifier(operationsDataInDto);
        ReportGenerator stringReport = new ReportGeneratorImpl();
        String report = stringReport.generateReport();
        FileWriter fileReport = new FileWriterImpl();
        fileReport.writeToFile(report, REPORT_FILE_NAME);
    }
}
