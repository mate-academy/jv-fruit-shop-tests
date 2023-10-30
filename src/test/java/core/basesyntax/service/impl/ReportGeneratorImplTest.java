package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.CalculateStrategy;
import core.basesyntax.strategy.OperationHandlerBalance;
import core.basesyntax.strategy.OperationHandlerIn;
import core.basesyntax.strategy.OperationHandlerOut;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    private static final String INPUT_FILE_NAME = "src/main/resources/fruits.csv";
    private static final String OUT_FILE_NAME = "src/main/resources/report.csv";

    private Map<FruitTransaction.Operation, OperationHandler>
            correspondenceTable = Map.of(
            FruitTransaction.Operation.BALANCE, new OperationHandlerBalance(),
            FruitTransaction.Operation.SUPPLY, new OperationHandlerIn(),
            FruitTransaction.Operation.RETURN, new OperationHandlerIn(),
            FruitTransaction.Operation.PURCHASE, new OperationHandlerOut());

    private FileReader fileReader = new FileReaderImpl();
    private CalculateStrategy calculateStrategy = new CalculateStrategy(correspondenceTable);
    private TransactionProcessor transactionProcessor
            = new TransactionProcessorImpl(calculateStrategy);
    private TransactionParser transactionParser = new TransactionParserImpl();
    private ReportGenerator reportGenerator = new ReportGeneratorImpl();
    private List<String> strings = fileReader.readFile(INPUT_FILE_NAME);
    private List<FruitTransaction> listOfTransaction = transactionParser.parseTransaction(strings);

    @Test
    void makeReportIs_Ok() {
        transactionProcessor.calculateBalance(listOfTransaction);
        String report = reportGenerator.makeReport();
        Assertions.assertEquals(report,"banana, 152"
                + System.lineSeparator()
                + "apple, 90"
                + System.lineSeparator());
    }

    @Test
    void makeReportIsNullNot_Ok() {
        transactionProcessor.calculateBalance(listOfTransaction);
        Assertions.assertNotNull(reportGenerator.makeReport());
    }
}
