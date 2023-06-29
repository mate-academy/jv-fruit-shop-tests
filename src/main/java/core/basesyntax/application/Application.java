package core.basesyntax.application;

import core.basesyntax.model.fruit.Operation;
import core.basesyntax.model.fruit.Record;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ReadParser;
import core.basesyntax.service.ReadService;
import core.basesyntax.service.WriteParser;
import core.basesyntax.service.WriteService;
import core.basesyntax.service.impl.CsvReadParserImpl;
import core.basesyntax.service.impl.CsvReadServiceImpl;
import core.basesyntax.service.impl.CsvWriteParserImpl;
import core.basesyntax.service.impl.CsvWriteServiceImpl;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {
    private ReadService readDao;
    private WriteService writeDao;
    private ReadParser readParser;
    private WriteParser writeParser;
    private OperationStrategy strategy;
    private FruitService fruitService;
    private final String inputPath = "src/main/resources/input/input.csv";
    private final String destinationPath = "src/main/resources/output/report.csv";

    public void run() {
        initialize();
        List<String> rowsFromFile = readDao.read(inputPath);
        List<Record> records = readParser.parseFileData(rowsFromFile);
        Map<String, Integer> fruitMap = fruitService.processRecords(records);
        String parsedFruitMap = writeParser.parseProcessedData(fruitMap);
        writeDao.save(parsedFruitMap, destinationPath);
    }

    private void initialize() {
        readParser = new CsvReadParserImpl();
        writeParser = new CsvWriteParserImpl();
        readDao = new CsvReadServiceImpl();
        writeDao = new CsvWriteServiceImpl();
        strategy = new OperationStrategy(initializeOperationMap());
        fruitService = new FruitServiceImpl(strategy);
    }

    public Map<Operation, OperationHandler> initializeOperationMap() {
        HashMap<Operation, OperationHandler> operationMap = new HashMap<>();
        operationMap.put(Operation.BALANCE, new BalanceHandler());
        operationMap.put(Operation.SUPPLY, new SupplyHandler());
        operationMap.put(Operation.PURCHASE, new PurchaseHandler());
        operationMap.put(Operation.RETURN, new ReturnHandler());
        return operationMap;
    }
}
