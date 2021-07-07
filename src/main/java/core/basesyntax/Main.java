package core.basesyntax;

import core.basesyntax.dto.ShopOperation;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.Parser;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.ParserImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.strategy.AddOperationHandler;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.SubtractOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String BALANCE = "b";
    public static final String SUPPLY = "s";
    public static final String RETURN = "r";
    public static final String PURCHASE = "p";
    private static final String INPUT_FILE_NAME = "src/main/resources/shop_operations.csv";
    private static final String OUTPUT_FILE_NAME = "src/main/resources/report.csv";

    public static void main(String[] args) {
        Map<String, OperationHandler> handlers = new HashMap<>();
        handlers.put(BALANCE, new BalanceOperationHandler());
        handlers.put(SUPPLY, new AddOperationHandler());
        handlers.put(RETURN, new AddOperationHandler());
        handlers.put(PURCHASE, new SubtractOperationHandler());

        FileReader reader = new FileReaderImpl();
        List<String> linesFromFile = reader.read(INPUT_FILE_NAME);

        Strategy strategy = new Strategy(handlers);
        Parser parser = new ParserImpl();

        for (int i = 1; i < linesFromFile.size(); i++) {
            ShopOperation shopOperation = parser.parseLine(linesFromFile.get(i));
            OperationHandler handler = strategy.getHandler(shopOperation);
            handler.apply(shopOperation);
        }

        ReportService reportService = new ReportServiceImpl();
        String report = reportService.makeReport();

        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(OUTPUT_FILE_NAME, report);
    }
}
