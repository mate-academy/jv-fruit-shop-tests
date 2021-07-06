package core.basesyntax;

import core.basesyntax.dbtest.Storage;
import core.basesyntax.service.CsvFileReader;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FruitReportService;
import core.basesyntax.service.Parser;
import core.basesyntax.service.ParserImpl;
import core.basesyntax.service.Validator;
import core.basesyntax.service.ValidatorCsv;
import core.basesyntax.service.Writer;
import core.basesyntax.strategy.AdditionHandler;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final String PATH_INPUT_FILE = "src/main/resources/storage.csv";
    private static final String PATH_OUTPUT_FILE = "src/main/resources/report.csv";

    public static void main(String[] args) {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new BalanceHandler(Storage.storage));
        operationHandlerMap.put("p", new PurchaseHandler(Storage.storage));
        operationHandlerMap.put("s", new AdditionHandler(Storage.storage));
        operationHandlerMap.put("r", new AdditionHandler(Storage.storage));

        FileReader fileReader = new CsvFileReader();
        List<String> infoFromFile = fileReader.readFromFile(PATH_INPUT_FILE);

        Validator validator = new ValidatorCsv();
        Parser parser = new ParserImpl(validator);

        infoFromFile.stream()
                .map(parser::parseToFruitDto)
                .forEach(fruitDto -> operationHandlerMap
                    .get(fruitDto.getOperation())
                    .apply(fruitDto)
                );
        FruitReportService report = new FruitReportService(Storage.storage);
        Writer fileWriter = new FileWriter();
        fileWriter.writeToFile(report.getReport(), PATH_OUTPUT_FILE);
    }
}
