package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.impl.DataConverterImpl;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.OperationProcessorImpl;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.ReportCreatorImpl;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.OperationProcessor;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String DATA_FILE_PATH = "src/main/resources/inputInfo.csv";
    private static final String REPORT_FILE_PATH = "src/main/resources/report.csv";
    private static final Map<OperationType, OperationHandler> OPERATION_HANDLER_MAP =
            new HashMap<>();

    static {
        OPERATION_HANDLER_MAP.put(OperationType.BALANCE, new BalanceOperation());
        OPERATION_HANDLER_MAP.put(OperationType.SUPPLY, new SupplyOperation());
        OPERATION_HANDLER_MAP.put(OperationType.PURCHASE, new PurchaseOperation());
        OPERATION_HANDLER_MAP.put(OperationType.RETURN, new ReturnOperation());
    }

    public static void main(String[] args) {
        FileReader fileReader = new FileReaderImpl();
        DataConverter dataConverter = new DataConverterImpl();
        OperationStrategy operationStrategy = new OperationStrategyImpl(OPERATION_HANDLER_MAP);
        OperationProcessor operationProcessor = new OperationProcessorImpl(operationStrategy);
        ReportCreator reportCreator = new ReportCreatorImpl();
        FileWriter fileWriter = new FileWriterImpl();

        List<String> inputInfo = fileReader.dataToProcess(DATA_FILE_PATH);
        List<FruitTransaction> fruitTransactionList = dataConverter.fruitList(inputInfo);
        operationProcessor.processConvertedData(fruitTransactionList);
        String report = reportCreator.createReport();
        fileWriter.writeReportToFile(REPORT_FILE_PATH, report);
    }
}
