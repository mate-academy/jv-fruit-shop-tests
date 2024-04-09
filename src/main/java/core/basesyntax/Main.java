package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import core.basesyntax.service.ConvertData;
import core.basesyntax.service.CsvReader;
import core.basesyntax.service.CsvWriter;
import core.basesyntax.service.ProcessData;
import core.basesyntax.service.ReportGenerate;
import core.basesyntax.service.impl.ConvertDataImpl;
import core.basesyntax.service.impl.CsvReaderImpl;
import core.basesyntax.service.impl.CsvWriterImpl;
import core.basesyntax.service.impl.ProcessDataImpl;
import core.basesyntax.service.impl.ReportGenerateImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.imp.OperationStrategyImp;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler()
        );
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImp(operationHandlerMap);
        ProcessData processData = new ProcessDataImpl(operationStrategy);
        CsvReader reader = new CsvReaderImpl();
        ConvertData convertData = new ConvertDataImpl();
        processData.operation(convertData.convertToTransaction(reader.read(
                "src/main/resources/reportToRead.csv")));
        ReportGenerate reportGenerate = new ReportGenerateImpl();
        CsvWriter csvWriter = new CsvWriterImpl();
        csvWriter.write(reportGenerate.report(), "src/main/resource/myReport");
    }
}
