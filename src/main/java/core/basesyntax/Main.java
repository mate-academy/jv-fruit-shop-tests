package core.basesyntax;

import core.basesyntax.model.FruitRecord;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileReaderImpl;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FileWriterImpl;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.FruitShopServiceImpl;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.ReportServiceImpl;
import core.basesyntax.service.operation.BalanceHandlerImpl;
import core.basesyntax.service.operation.FruitParser;
import core.basesyntax.service.operation.Handler;
import core.basesyntax.service.operation.PurchaseHandlerImpl;
import core.basesyntax.service.operation.ReturnHandlerImpl;
import core.basesyntax.service.operation.SupplyHandlerImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String INPUT = "src/main/resources/input.csv";
    public static final String OUTPUT = "src/main/resources/report.csv";

    public static void main(String[] args) {
        FileReader fileReader = new FileReaderImpl();
        Map<Operation, Handler> handlerMap = new HashMap<>();
        handlerMap.put(Operation.BALANCE, new BalanceHandlerImpl());
        handlerMap.put(Operation.SUPPLY, new SupplyHandlerImpl());
        handlerMap.put(Operation.PURCHASE, new PurchaseHandlerImpl());
        handlerMap.put(Operation.RETURN, new ReturnHandlerImpl());
        FruitParser fruitParser = new FruitParser();
        List<String> input = fileReader.getFileData(INPUT);
        List<FruitRecord> fruitRecordList = fruitParser.createDto(input);
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlerMap);
        FruitShopService fruitShopService = new FruitShopServiceImpl(operationStrategy);
        fruitShopService.transfer(fruitRecordList);
        ReportService reportService = new ReportServiceImpl();
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeFile(OUTPUT, reportService.createReport());
    }
}
