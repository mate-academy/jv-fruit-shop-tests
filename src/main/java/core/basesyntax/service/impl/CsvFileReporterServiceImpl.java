package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReporterService;
import core.basesyntax.service.OperationStrategyService;
import java.util.List;
import java.util.function.Consumer;

public class CsvFileReporterServiceImpl implements FileReporterService, Consumer<String> {
    private static final String COMMA = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private OperationStrategyService operationStrategy;
    private Storage storage;

    public CsvFileReporterServiceImpl(OperationStrategyService operationStrategy, Storage storage) {
        this.operationStrategy = operationStrategy;
        this.storage = storage;
    }

    @Override
    public void getReport(List<String> readFile) {
        readFile
                .stream()
                .forEach(this);
    }

    private void changeValueDependingOnOperation(FruitTransaction transaction) {
        storage.getFruitQuantityMap().put(transaction.getFruit(),
                operationStrategy.getOperationHandler(transaction.getOperation())
                                .handle(transaction, storage));
    }

    @Override
    public void accept(String operation) {
        String[] dividedByComma = operation.split(COMMA);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.fromString(dividedByComma[OPERATION_INDEX]),
                dividedByComma[FRUIT_NAME_INDEX],
                Integer.parseInt(dividedByComma[QUANTITY_INDEX]));
        changeValueDependingOnOperation(transaction);
    }
}
