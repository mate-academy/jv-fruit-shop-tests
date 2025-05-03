package core.basesyntax;

import static core.basesyntax.model.Operation.BALANCE;
import static core.basesyntax.model.Operation.PURCHASE;
import static core.basesyntax.model.Operation.RETURN;
import static core.basesyntax.model.Operation.SUPPLY;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.OperationValidator;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.OperationValidatorImpl;
import core.basesyntax.service.impl.ReportGenerationImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import core.basesyntax.service.impl.TransactionServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.OperationHandlerBalanceImpl;
import core.basesyntax.strategy.impl.OperationHandlerPurchaseImpl;
import core.basesyntax.strategy.impl.OperationHandlerReturnImpl;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.OperationHandlerSupplyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String WRITE_FROM = "src/main/resources/input.csv";
    private static final String WRITE_TO = "src/main/resources/result.csv";
    private static final OperationValidator validator = new OperationValidatorImpl();

    public static void main(String[] args) {

        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(BALANCE.chooseOperation(), new OperationHandlerBalanceImpl());
        operationHandlerMap.put(PURCHASE.chooseOperation(), new OperationHandlerPurchaseImpl());
        operationHandlerMap.put(RETURN.chooseOperation(), new OperationHandlerReturnImpl());
        operationHandlerMap.put(SUPPLY.chooseOperation(), new OperationHandlerSupplyImpl());

        FileReader getDataBase = new FileReaderImpl();
        List<String> list = getDataBase.readFromFile(WRITE_FROM);

        TransactionParser transactionParser = new TransactionParserImpl(validator);
        List<FruitTransaction> transactions = transactionParser.parseTransactions(list);

        TransactionService insertDataToBase =
                new TransactionServiceImpl(new OperationHandlerStrategyImpl(operationHandlerMap));
        insertDataToBase.addTransferToStorage(transactions);

        ReportGenerator reportGeneration = new ReportGenerationImpl();
        String report = reportGeneration.generateReport(FruitStorage.fruitStorage);

        FileWriter writeDataToFile = new FileWriterImpl();
        writeDataToFile.writeDataToFile(report, WRITE_TO);
    }
}
