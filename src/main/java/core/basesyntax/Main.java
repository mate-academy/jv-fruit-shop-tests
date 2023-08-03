package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataProcessingServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportParserImpl;
import core.basesyntax.service.impl.TransactionStrategyImpl;
import core.basesyntax.service.impl.TransactionValidatorImpl;
import core.basesyntax.service.impl.TransactionsParserImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.service.interfaces.DataProcessingService;
import core.basesyntax.service.interfaces.ReaderService;
import core.basesyntax.service.interfaces.TransactionParser;
import core.basesyntax.service.interfaces.TransactionStrategy;
import core.basesyntax.service.interfaces.WriterService;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        ReaderService readerService = new ReaderServiceImpl();
        String inputData = readerService.readFromFile("dataTxt/input.txt");
        TransactionParser<List<FruitTransaction>, String> transactionParser =
                new TransactionsParserImpl(new TransactionValidatorImpl());
        List<FruitTransaction> transactions = transactionParser.parse(inputData);
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl();
        DataProcessingService dataProcessingService = new DataProcessingServiceImpl(
                transactionStrategy);
        dataProcessingService.processData(transactions);
        WriterService writerService = new WriterServiceImpl();
        TransactionParser<String, Map<Fruit, Integer>> reportParser = new ReportParserImpl();
        String parsedRecords = reportParser.parse(Storage.getAll());
        writerService.writeToFile("dataTxt/report.txt", parsedRecords);
    }
}
