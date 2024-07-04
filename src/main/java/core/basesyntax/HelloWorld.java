package core.basesyntax;

import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.parse.FruitRecordParser;
import core.basesyntax.service.parse.FruitRecordParserImpl;
import core.basesyntax.service.processor.DataProcessorService;
import core.basesyntax.service.processor.DataProcessorServiceImpl;
import core.basesyntax.service.read.FileReader;
import core.basesyntax.service.read.FileReaderImpl;
import core.basesyntax.service.report.ReportService;
import core.basesyntax.service.report.ReportServiceImpl;
import core.basesyntax.service.strategy.TypeStrategy;
import core.basesyntax.service.strategy.TypeStrategyImpl;
import core.basesyntax.service.strategy.strategyimpl.BalanceStrategy;
import core.basesyntax.service.strategy.strategyimpl.PurchaseStrategy;
import core.basesyntax.service.strategy.strategyimpl.ReturnStrategy;
import core.basesyntax.service.strategy.strategyimpl.SupplyStrategy;
import core.basesyntax.service.strategy.strategyimpl.TypeService;
import core.basesyntax.service.write.FileWriterService;
import core.basesyntax.service.write.FileWriterServiceImpl;
import java.util.HashMap;
import java.util.List;

public class HelloWorld {
    private static final String PATH_TO_READ = "src/main/resources/test.csv";
    private static final String PATH_TO_WRITE = "src/main/resources/report.cvs";

    public static void main(String[] args) {
        FileReader fileReader = new FileReaderImpl();
        String[] lines = fileReader.readFile(PATH_TO_READ);

        HashMap<FruitRecord.Operation, TypeService> typeServiceMap = new HashMap<>();
        typeServiceMap.put(FruitRecord.Operation.BALANCE, new BalanceStrategy());
        typeServiceMap.put(FruitRecord.Operation.PURCHASE, new PurchaseStrategy());
        typeServiceMap.put(FruitRecord.Operation.RETURN, new ReturnStrategy());
        typeServiceMap.put(FruitRecord.Operation.SUPPLY, new SupplyStrategy());
        TypeStrategy typeStrategy = new TypeStrategyImpl(typeServiceMap);

        FruitRecordParser fruitRecordParser = new FruitRecordParserImpl();
        List<FruitRecord> fruitRecords = fruitRecordParser.parseFruitRecords(lines);

        DataProcessorService dataProcessorService = new DataProcessorServiceImpl(typeStrategy);
        dataProcessorService.processData(fruitRecords);

        ReportService createReport = new ReportServiceImpl();
        String report = createReport.getReport();

        FileWriterService fileWrite = new FileWriterServiceImpl();
        fileWrite.writeToFile(report, PATH_TO_WRITE);
    }
}
