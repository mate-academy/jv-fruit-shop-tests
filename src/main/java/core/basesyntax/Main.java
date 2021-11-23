package core.basesyntax;

import core.basesyntax.dao.FruitRecordDao;
import core.basesyntax.dao.FruitRecordDaoImpl;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileReaderImpl;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FileWriterImpl;
import core.basesyntax.service.FruitRecordParser;
import core.basesyntax.service.FruitRecordParserImpl;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import core.basesyntax.service.operation.BalanceHandlerImpl;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseHandlerImpl;
import core.basesyntax.service.operation.ReturnHandlerImpl;
import core.basesyntax.service.operation.SupplyHandlerImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String FROM_FILE = "src/main/resources/fruitshop.csv";
    public static final String TO_FILE_PATH = "src/main/resources/dailyfruitshop.csv";

    public static void main(String[] args) {
        FileReader fileRead = new FileReaderImpl();
        FruitRecordParser fruitRecordParser = new FruitRecordParserImpl();
        FruitRecordDao fruitRecordDao = new FruitRecordDaoImpl();
        List<String> fileLines = fileRead.readFromFile(FROM_FILE);
        List<FruitRecord> fruitRecordsList = fruitRecordParser.parserFruitRecords(fileLines);
        fruitRecordDao.saveAll(fruitRecordsList);
        Map<String, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put("b", new BalanceHandlerImpl());
        operationHandlersMap.put("s", new SupplyHandlerImpl());
        operationHandlersMap.put("p", new PurchaseHandlerImpl());
        operationHandlersMap.put("r", new ReturnHandlerImpl());
        List<FruitRecord> fruitRecordList = fruitRecordDao.getAll();
        for (FruitRecord fruitRecord : fruitRecordList) {
            OperationHandler operationHandler
                    = operationHandlersMap.get(fruitRecord.getOperationType());
            operationHandler.applyOperation(fruitRecord);
        }
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String toFileString = reportGenerator.generateReport(fruitRecordDao.getFruitMap());
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeToFile(TO_FILE_PATH, toFileString);
    }
}
