package core.basesyntax;

import core.basesyntax.enums.Operation;
import core.basesyntax.filework.ReportWriteToFile;
import core.basesyntax.filework.ShopFileReader;
import core.basesyntax.filework.impl.ReportWriteToFileImpl;
import core.basesyntax.filework.impl.ShopFileReaderImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.FruitTransactionData;
import core.basesyntax.service.ReportMaker;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.FruitTransactionDataImpl;
import core.basesyntax.service.impl.ReportMakerImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.impl.BalanceService;
import core.basesyntax.strategy.impl.OperationHandler;
import core.basesyntax.strategy.impl.PurchaseService;
import core.basesyntax.strategy.impl.ReturnService;
import core.basesyntax.strategy.impl.SupplyService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String INPUT_FILE_PATH = "src/main/resources/fruitShopStorageFile.csv";
    private static final String OUTPUT_DATA_FILE = "src/main/resources/writeToFile.csv";

    public static void main(String[] args) {
        Map<Operation, OperationHandler> operationServiceMap = new HashMap<>();
        operationServiceMap.put(Operation.BALANCE, new BalanceService());
        operationServiceMap.put(Operation.SUPPLY, new SupplyService());
        operationServiceMap.put(Operation.RETURN, new ReturnService());
        operationServiceMap.put(Operation.PURCHASE, new PurchaseService());

        ShopFileReader shopFileReader = new ShopFileReaderImpl();
        List<String> dataFromFile = shopFileReader.readFromFile(INPUT_FILE_PATH);

        FruitTransactionData fruitTransactionData = new FruitTransactionDataImpl();
        List<FruitTransaction> fruitTransactions
                = fruitTransactionData.parseDataFromFile(dataFromFile);

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationServiceMap);
        FruitService fruitService = new FruitServiceImpl(operationStrategy);
        fruitService.doOperationService(fruitTransactions);

        ReportMaker reportMaker = new ReportMakerImpl();
        String report = reportMaker.makeReport();
        ReportWriteToFile reportWriteToFile = new ReportWriteToFileImpl();
        reportWriteToFile.writeToFile(report, OUTPUT_DATA_FILE);
    }
}
