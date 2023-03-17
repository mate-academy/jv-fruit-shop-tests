package core.basesyntax;

import core.basesyntax.model.FruitsInStore;
import core.basesyntax.model.OperationType;
import core.basesyntax.services.CsvFileReaderService;
import core.basesyntax.services.CsvFileWriterService;
import core.basesyntax.services.DataParcerService;
import core.basesyntax.services.DataProcessingService;
import core.basesyntax.services.ParametrsValidatorService;
import core.basesyntax.services.ShopReportService;
import core.basesyntax.services.impl.CsvFileReaderServiceImpl;
import core.basesyntax.services.impl.CsvFileWriterServiceImpl;
import core.basesyntax.services.impl.DataParcerServiceImpl;
import core.basesyntax.services.impl.DataProcessingServiceImpl;
import core.basesyntax.services.impl.ParametrsValidatorServiseImpl;
import core.basesyntax.services.impl.ShopReportServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperarionHandlerPurchase;
import core.basesyntax.strategy.impl.OperationHandlerBalance;
import core.basesyntax.strategy.impl.OperationHandlerReturn;
import core.basesyntax.strategy.impl.OperationHandlersSupply;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static ParametrsValidatorService parametrsValidator;
    private static List<String> opratorTypeCode;
    private static List<String> fruitsInStore;
    private static Map<String, OperationHandler> operationHandlerMap;
    private static final String INPUT_DATA_PATH = "src/main/resources/FruitShopInputData.csv";
    private static final String REPORT_DATA_PATH = "src/main/resources/FruitShopOutputData.csv";

    public static void main(String[] args) {
        opratorTypeCode = Arrays.stream(OperationType.values())
                .map(OperationType::getCode)
                .collect(Collectors.toList());

        fruitsInStore = Arrays.stream(FruitsInStore.values())
                .map(FruitsInStore::getCode)
                .collect(Collectors.toList());
        parametrsValidator = new ParametrsValidatorServiseImpl(opratorTypeCode, fruitsInStore);
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE.getCode(),new OperationHandlerBalance());
        operationHandlerMap.put(OperationType.SUPPLY.getCode(),new OperationHandlersSupply());
        operationHandlerMap.put(OperationType.RETURN.getCode(),new OperationHandlerReturn());
        operationHandlerMap.put(OperationType.PURCHASE.getCode(),new OperarionHandlerPurchase());

        OperationStrategy operationStrategy = new OperationStrategyImpl(
                operationHandlerMap,
                parametrsValidator);

        CsvFileReaderService readerService = new CsvFileReaderServiceImpl();
        List<String> inputData = readerService.readFromFile(Path.of(INPUT_DATA_PATH));

        ParametrsValidatorService parametrsValidator =
                new ParametrsValidatorServiseImpl(opratorTypeCode,fruitsInStore);

        DataParcerService dataParcerService = new DataParcerServiceImpl(parametrsValidator);
        List<List<String>> parcedData = dataParcerService.parceDataFromCsv(inputData);

        DataProcessingService dataProcessingService =
                new DataProcessingServiceImpl(operationStrategy, parametrsValidator);
        Map<String, Integer> processedData = dataProcessingService.processData(parcedData);

        ShopReportService reportService = new ShopReportServiceImpl();
        List<String> report = reportService.generateReport(processedData);

        CsvFileWriterService writerService = new CsvFileWriterServiceImpl();
        writerService.writeToCsvFile(report, Path.of(REPORT_DATA_PATH));
    }
}
