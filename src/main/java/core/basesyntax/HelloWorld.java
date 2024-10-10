package core.basesyntax;

import core.basesyntax.io.FileReader;
import core.basesyntax.io.FileReaderImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.DataConverterImpl;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloWorld {
    FileReader fileReader = new FileReaderImpl();
    List<String> inputReport = fileReader.read("reportToRead.csv");

    DataConverter dataConverter = new DataConverterImpl();
    List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);

    Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceOperation(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
            FruitTransaction.Operation.RETURN, new ReturnOperation()
    );
    OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
    Map<String, Integer> storage = new HashMap<>();
}
