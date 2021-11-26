package core.basesyntax;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.Parser;
import core.basesyntax.service.Reader;
import core.basesyntax.service.Writer;
import core.basesyntax.service.impl.CsvReaderImpl;
import core.basesyntax.service.impl.ParserImpl;
import core.basesyntax.service.impl.ReportCreatorImpl;
import core.basesyntax.service.impl.ValidatorImpl;
import core.basesyntax.service.impl.WriterImpl;
import core.basesyntax.strategy.AddOperationHandler;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String DATABASE_PATH = "src/main/java/resources/database.csv";
    private static final String REPORT_FILE_PATH = "src/main/java/resources/report.csv";

    public static void main(String[] args) {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("s", new AddOperationHandler());
        operationHandlerMap.put("p", new PurchaseOperationHandler());
        operationHandlerMap.put("r", new AddOperationHandler());
        operationHandlerMap.put("b", new BalanceOperationHandler());
        Reader reader = new CsvReaderImpl();
        List<String> lines = reader.readFromFile(DATABASE_PATH);
        Parser<TransactionDto> parser = new ParserImpl(new ValidatorImpl());
        List<TransactionDto> transactionDto = parser.parseLines(lines);
        for (TransactionDto transaction : transactionDto) {
            String operation = transaction.getOperation();
            OperationHandler handler = operationHandlerMap.get(operation);
            handler.apply(transaction);
        }
        List<String> report = new ReportCreatorImpl().createReport();
        Writer writer = new WriterImpl();
        writer.writeToFile(report, REPORT_FILE_PATH);
    }
}
