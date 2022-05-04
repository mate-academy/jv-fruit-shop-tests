package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import core.basesyntax.servise.converter.Converter;
import core.basesyntax.servise.converter.ConverterImpl;
import core.basesyntax.servise.reader.Reader;
import core.basesyntax.servise.reader.ReaderImpl;
import core.basesyntax.servise.reporter.Reporter;
import core.basesyntax.servise.reporter.ReporterImpl;
import core.basesyntax.servise.strategy.StrategyOperation;
import core.basesyntax.servise.strategy.StrategyOperationImpl;
import core.basesyntax.servise.writer.Writer;
import core.basesyntax.servise.writer.WriterImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String PATH_INPUT_FILE = "src/main/resources/inputData.csv";
    private static final String PATH_REPORT_FILE = "src/main/resources/report.csv";

    public static void main(String[] args) {
        Map<String, OperationHandler> map = new HashMap<>();
        map.put("b", new BalanceOperationHandler());
        map.put("s", new SupplyOperationHandler());
        map.put("r", new ReturnOperationHandler());
        map.put("p", new PurchaseOperationHandler());

        Reader readFromFile = new ReaderImpl();
        List<String> inputFromFile = readFromFile.readFromFile(PATH_INPUT_FILE);

        Converter convertToObject = new ConverterImpl();
        List<FruitTransaction> fruitTransactions = convertToObject.convert(inputFromFile);

        StrategyOperation strategyOperation = new StrategyOperationImpl(map);
        for (FruitTransaction fruits : fruitTransactions) {
            strategyOperation.getOperation(fruits).process(fruits);
        }

        Reporter reportService = new ReporterImpl();
        String reportString = reportService.createReport();

        Writer fileWriter = new WriterImpl();
        fileWriter.writeToFile(PATH_REPORT_FILE, reportString);
    }
}
