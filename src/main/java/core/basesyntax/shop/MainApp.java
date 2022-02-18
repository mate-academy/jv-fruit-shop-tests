package core.basesyntax.shop;

import core.basesyntax.shop.impl.FruitTransaction;
import core.basesyntax.shop.service.FileReaderService;
import core.basesyntax.shop.service.FileReaderServiceImpl;
import core.basesyntax.shop.service.FileWriterService;
import core.basesyntax.shop.service.FileWriterServiceImpl;
import core.basesyntax.shop.service.ReportWriter;
import core.basesyntax.shop.service.ReportWriterImpl;
import core.basesyntax.shop.service.RowParser;
import core.basesyntax.shop.service.RowParserImpl;
import core.basesyntax.shop.strategy.AddOperationHandler;
import core.basesyntax.shop.strategy.BalanceOperationHandler;
import core.basesyntax.shop.strategy.OperationHandler;
import core.basesyntax.shop.strategy.Strategy;
import core.basesyntax.shop.strategy.SubtractOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainApp {
    public static final String BALANCE = "b";
    public static final String SUPPLY = "s";
    public static final String RETURN = "r";
    public static final String PURCHASE = "p";
    private static final String INPUT_FILE = "src/main/java/core/basesyntax/resources/input.csv";
    private static final String OUTPUT_FILE = "src/main/java/core/basesyntax/resources/report.csv";
    private static final int START_LINE = 1;
    private static final Map<String, OperationHandler> operationsData = new HashMap<>();

    static {
        operationsData.put(BALANCE, new BalanceOperationHandler());
        operationsData.put(SUPPLY, new AddOperationHandler());
        operationsData.put(RETURN, new AddOperationHandler());
        operationsData.put(PURCHASE, new SubtractOperationHandler());
    }

    public static void main(String[] args) {
        FileReaderService fileReader = new FileReaderServiceImpl();
        List<String> linesFromFile = fileReader.read(INPUT_FILE);
        Strategy strategy = new Strategy(operationsData);
        RowParser parser = new RowParserImpl();
        for (int i = START_LINE; i < linesFromFile.size(); i++) {
            FruitTransaction shopOperation = parser.parseLine(linesFromFile.get(i));
            OperationHandler handler = strategy.getHandler(shopOperation);
            handler.apply(shopOperation);
        }
        ReportWriter reportWriter = new ReportWriterImpl();
        String report = reportWriter.makeReport();
        FileWriterService fileWriter = new FileWriterServiceImpl();
        fileWriter.write(OUTPUT_FILE, report);
    }
}
