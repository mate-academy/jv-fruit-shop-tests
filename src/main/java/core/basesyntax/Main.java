package core.basesyntax;

import core.basesyntax.model.Operation;
import core.basesyntax.service.DataParserService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportGeneratorService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.DataParserServiceImpl;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.ReportGeneratorServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final String fromFile = "src/main/resources/input.csv";
    private static final String toFile = "src/main/resources/output.csv";

    public static void main(String[] args) {
        Map<String, OperationHandler> operationServiceMap = new HashMap<>();
        operationServiceMap.put(Operation.BALANCE.getOperation(),
                new BalanceOperationImpl());
        operationServiceMap.put(Operation.SUPPLY.getOperation(),
                new SupplyOperationImpl());
        operationServiceMap.put(Operation.PURCHASE.getOperation(),
                new PurchaseOperationImpl());
        operationServiceMap.put(Operation.RETURN.getOperation(),
                new ReturnOperationImpl());
        ReaderService readerService;
        String readData;
        try {
            readerService = new FileReaderServiceImpl(
                    new BufferedReader(new FileReader(fromFile)));
            readData = readerService.read();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationServiceMap);
        DataParserService dataParserService = new DataParserServiceImpl(operationStrategy);
        Map<String, Integer> parseDataMap = dataParserService.parseData(readData);
        ReportGeneratorService generatorService = new ReportGeneratorServiceImpl();
        String report = generatorService.generateReport(parseDataMap);
        WriterService writerService;
        try {
            writerService = new FileWriterServiceImpl(
                    new BufferedWriter(new FileWriter(toFile)));
            writerService.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Cannot create file");
        }
    }
}
