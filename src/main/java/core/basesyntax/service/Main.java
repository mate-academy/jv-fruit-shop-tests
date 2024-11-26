package core.basesyntax.service;

import core.basesyntax.dataprocessor.BalanceHandler;
import core.basesyntax.dataprocessor.DataConverter;
import core.basesyntax.dataprocessor.DataProcessor;
import core.basesyntax.dataprocessor.DefaultDataOperationStrategy;
import core.basesyntax.dataprocessor.Operation;
import core.basesyntax.dataprocessor.OperationHandler;
import core.basesyntax.dataprocessor.PurchaseHandler;
import core.basesyntax.dataprocessor.ReturnHandler;
import core.basesyntax.dataprocessor.SupplyHandler;
import core.basesyntax.fileprocessor.CsvFileReader;
import core.basesyntax.fileprocessor.FileReader;
import core.basesyntax.fileprocessor.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_PATH = "input.csv";
    private static final String OUTPUT_FILE_PATH = "output.csv";

    public static void main(String[] args) {
        FruitDB fruitDB = new FruitDB();

        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.BALANCE, new BalanceHandler(fruitDB));
        handlers.put(Operation.SUPPLY, new SupplyHandler(fruitDB));
        handlers.put(Operation.PURCHASE, new PurchaseHandler(fruitDB));
        handlers.put(Operation.RETURN, new ReturnHandler(fruitDB));

        DefaultDataOperationStrategy operationStrategy = new DefaultDataOperationStrategy(handlers);

        FileReader fileReader = new CsvFileReader();
        DataConverter dataConverter = new DataConverter();
        DataProcessor dataProcessor = new DataProcessor(operationStrategy);
        ReportGenerator reportGenerator = new ReportGenerator(fruitDB);
        FileWriter fileWriter = new FileWriter();

        FruitShop fruitShop = new FruitShop(
                fileReader, dataConverter, dataProcessor, reportGenerator, fileWriter
        );

        fruitShop.run(INPUT_FILE_PATH, OUTPUT_FILE_PATH);
    }
}
