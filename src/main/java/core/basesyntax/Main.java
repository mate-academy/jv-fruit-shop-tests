package core.basesyntax;

import core.basesyntax.model.FruitRecord;
import core.basesyntax.operationtype.BalanceHandler;
import core.basesyntax.operationtype.OperationHandler;
import core.basesyntax.operationtype.PurchaseHandler;
import core.basesyntax.operationtype.ReturnHandler;
import core.basesyntax.operationtype.SupplyHandler;
import core.basesyntax.service.FruitCounter;
import core.basesyntax.service.FruitCounterImpl;
import core.basesyntax.service.FruitRecordParser;
import core.basesyntax.service.FruitRecordParserImpl;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.read.FileReader;
import core.basesyntax.service.read.FileReaderImpl;
import core.basesyntax.service.write.FileWriter;
import core.basesyntax.service.write.FileWriterImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String PATH_TO_READ = "src/main/resources/inputFile.csv";
    private static final String PATH_TO_WRITE = "src/main/resources/report.csv";

    public static void main(String[] args) {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new BalanceHandler());
        operationHandlerMap.put("p", new PurchaseHandler());
        operationHandlerMap.put("r", new ReturnHandler());
        operationHandlerMap.put("s", new SupplyHandler());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        FileReader fileReader = new FileReaderImpl();
        FruitRecordParser fruitRecordParser = new FruitRecordParserImpl();
        FruitCounter fruitCounter = new FruitCounterImpl(operationStrategy);
        FileWriter fileWriter = new FileWriterImpl();
        List<String[]> extractedInformation = fileReader.split(fileReader.read(PATH_TO_READ));
        List<FruitRecord> fruitRecords = fruitRecordParser.parse(extractedInformation);
        fruitCounter.countFruit(fruitRecords);
        String report = fileWriter.prepareToWrite();
        fileWriter.write(report, PATH_TO_WRITE);
    }
}
