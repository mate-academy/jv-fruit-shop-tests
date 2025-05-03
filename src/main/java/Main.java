import core.basesyntax.handler.BalanceHandlerImpl;
import core.basesyntax.handler.PurchaseHandlerImpl;
import core.basesyntax.handler.ReturnOperationHandlerImpl;
import core.basesyntax.handler.SupplierHandlerImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.DataParserServiceImpl;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.impl.ReadDataServiceImpl;
import core.basesyntax.service.impl.ReportCreationServiceImpl;
import core.basesyntax.service.impl.WriteDataServiceImpl;
import core.basesyntax.service.strategy.DataParserService;
import core.basesyntax.service.strategy.FruitShopService;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.ReadDataService;
import core.basesyntax.service.strategy.ReportCreationService;
import core.basesyntax.service.strategy.WriteDataService;
import core.basesyntax.service.strategy.impl.OperationStrategyImpl;
import core.basesyntax.util.DataValidator;
import java.util.List;
import java.util.Map;

public class Main {
    private static String INPUT_DATA_FILE_NAME = "src/main/resources/inputData.csv";
    private static String REPORT_FILE_NAME = "src/main/resources/outputData.csv";

    private static final Map<Operation, OperationHandler> operationStarategyMap = Map.of(
            Operation.BALANCE, new BalanceHandlerImpl(),
            Operation.PURCHASE, new PurchaseHandlerImpl(),
            Operation.SUPPLY, new SupplierHandlerImpl(),
            Operation.RETURN, new ReturnOperationHandlerImpl());

    public static void main(String[] args) {
        DataValidator dataValidator = new DataValidator();
        ReadDataService fileReader = new ReadDataServiceImpl(dataValidator);
        List<String> dataFromFile = fileReader.readData(INPUT_DATA_FILE_NAME);
        DataParserService dataParserService = new DataParserServiceImpl(dataValidator);
        List<FruitTransaction> fruitTransaction = dataParserService
                .createFruitTransaction(dataFromFile);
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationStarategyMap);
        FruitShopService fruitShopService = new FruitShopServiceImpl(operationStrategy);
        fruitShopService.processData(fruitTransaction);
        ReportCreationService reportCreationService = new ReportCreationServiceImpl();
        WriteDataService writeDataService = new WriteDataServiceImpl();
        String report = reportCreationService.getReport();
        writeDataService.writeData(report, REPORT_FILE_NAME);
    }
}
