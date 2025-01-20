package core.basesyntax;

import core.basesyntax.dao.Storage;
import core.basesyntax.fao.CustomFileReader;
import core.basesyntax.fao.CustomFileReaderImpl;
import core.basesyntax.fao.CustomFileWriter;
import core.basesyntax.fao.FileWriterImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.ReportGenerator;
import core.basesyntax.model.ReportGeneratorImpl;
import core.basesyntax.model.convertor.DataConvertor;
import core.basesyntax.model.convertor.DataConvertorImpl;
import core.basesyntax.model.handler.BalanceOperation;
import core.basesyntax.model.handler.OperationHandler;
import core.basesyntax.model.handler.PurchaseOperation;
import core.basesyntax.model.handler.ReturnOperation;
import core.basesyntax.model.handler.SupplyOperation;
import core.basesyntax.model.strategy.OperationStrategy;
import core.basesyntax.model.strategy.OperationStrategyImpl;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        CustomFileReader customFileReader = new CustomFileReaderImpl();
        List<String> inputReport = customFileReader.read();

        DataConvertor dataConverter = new DataConvertorImpl();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();

        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        ShopService shopService = new ShopServiceImpl(operationStrategy);
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);
        shopService.process(transactions);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String resultingReport = reportGenerator.getReport(Storage.storage);

        CustomFileWriter customFileWriter = new FileWriterImpl();
        customFileWriter.write(resultingReport);
    }

}
