package core.basesyntax.service;

import core.basesyntax.dataprocessor.BalanceHandler;
import core.basesyntax.dataprocessor.DataConverter;
import core.basesyntax.dataprocessor.DataProcessor;
import core.basesyntax.dataprocessor.DefaultDataOperationStrategy;
import core.basesyntax.dataprocessor.Operation;
import core.basesyntax.dataprocessor.PurchaseHandler;
import core.basesyntax.dataprocessor.ReturnHandler;
import core.basesyntax.dataprocessor.SupplyHandler;
import core.basesyntax.fileprocessor.FileReader;
import core.basesyntax.fileprocessor.FileWriter;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE = "input.csv";
    private static final String OUTPUT_FILE = "output.csv";

    public static void main(String[] args) {
        FruitDB fruitDB = FruitDB.getInstance();
        FileReader fileReader = new FileReader();
        FileWriter fileWriter = new FileWriter();
        DataConverter dataConverter = new DataConverter();
        DefaultDataOperationStrategy operationStrategy = new DefaultDataOperationStrategy(
                Map.of(
                        Operation.BALANCE, new BalanceHandler(),
                        Operation.SUPPLY, new SupplyHandler(),
                        Operation.PURCHASE, new PurchaseHandler(),
                        Operation.RETURN, new ReturnHandler()
                )
        );
        DataProcessor dataProcessor = new DataProcessor(operationStrategy);
        ReportGenerator reportGenerator = new ReportGenerator(fruitDB);
        FruitShop fruitShop = new FruitShop(
                fileReader, dataConverter, dataProcessor, reportGenerator, fileWriter
        );
        fruitShop.run(INPUT_FILE, OUTPUT_FILE);
    }
}
