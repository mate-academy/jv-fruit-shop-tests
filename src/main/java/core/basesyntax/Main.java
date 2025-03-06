package core.basesyntax;

import core.basesyntax.dao.CsvReaderImpl;
import core.basesyntax.dao.CsvWriterImpl;
import core.basesyntax.dao.CustomFileReader;
import core.basesyntax.dao.CustomFileWriter;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationsList;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.impl.BalanceCalculatorImpl;
import core.basesyntax.service.impl.BalanceHandler;
import core.basesyntax.service.impl.DataFruitConverterImpl;
import core.basesyntax.service.impl.FileFormaterForCsvReader;
import core.basesyntax.service.impl.PurchaseHandler;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.ReturnHandler;
import core.basesyntax.service.impl.SupplyHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String INPUT_FILE_NAME = "src/main/resources/reportToRead.csv";
    public static final String OUTPUT_FILE_NAME = "src/main/resources/reportToWrite.csv";

    public static void main(String[] args) {
        CustomFileReader fileReader = new CsvReaderImpl();
        FileFormaterForCsvReader fileFormater = new FileFormaterForCsvReader(fileReader);

        DataConverter dataConverter = new DataFruitConverterImpl();
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(
                fileFormater.parseCsv(INPUT_FILE_NAME));

        Map<OperationsList, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(OperationsList.BALANCE, new BalanceHandler());
        operationHandlers.put(OperationsList.PURCHASE, new PurchaseHandler());
        operationHandlers.put(OperationsList.RETURN, new ReturnHandler());
        operationHandlers.put(OperationsList.SUPPLY, new SupplyHandler());
        OperationStrategy operationStrategy = new OperationStrategy(operationHandlers);

        Operation operation = new BalanceCalculatorImpl(operationStrategy);
        ReportCreator reportCreator = new ReportGeneratorImpl();
        List<String> finalReport = reportCreator
                .createReport(operation.update(transactions));
        CustomFileWriter fileWriter = new CsvWriterImpl();
        fileWriter.writeFile(OUTPUT_FILE_NAME, finalReport);
    }
}
