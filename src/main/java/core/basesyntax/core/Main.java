package core.basesyntax.core;

import core.basesyntax.operation.AdditionHandler;
import core.basesyntax.operation.DecreaseHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import core.basesyntax.service.FileReaderImpl;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FileWriterImpl;
import core.basesyntax.service.RecordParser;
import core.basesyntax.service.RecordParserImpl;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.ReportCreatorImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_PATH = "src/main/resources/input.csv";
    private static final String REPORT_PATH = "src/main/resources/report.csv";
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";

    public static void main(String[] args) {
        List<String> fileData = new FileReaderImpl().read(INPUT_PATH);
        RecordParser recordParser = new RecordParserImpl();
        recordParser.parseRecords(fileData);
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(BALANCE, new AdditionHandler());
        operationHandlerMap.put(SUPPLY, new AdditionHandler());
        operationHandlerMap.put(PURCHASE, new DecreaseHandler());
        operationHandlerMap.put(RETURN, new AdditionHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        ReportCreator reportCreator = new ReportCreatorImpl(operationStrategy);
        List<String> newReport = reportCreator.createReport();
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(newReport, REPORT_PATH);
    }
}
