package core.basesyntax.service.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.Operations;
import java.util.ArrayList;
import java.util.List;

public class FruitServiceImpl implements FruitService {
    private static final String HEADER_LINE = "fruit,quantity";
    private static final String COMMA = ",";
    private final FruitStorage fruitStorage;
    private final List<String> errorMessages;

    public FruitServiceImpl(FruitStorage fruitStorage) {
        this.fruitStorage = fruitStorage;
        this.errorMessages = new ArrayList<>();
    }

    @Override
    public void processTransactions(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            if (transaction.getOperation() == null) {
                errorMessages.add("Invalid operation: null");
                continue;
            }
            Operations operation = Operations.valueOf(transaction.getOperation()
                    .toString().toUpperCase());
            OperationHandler handler = operation.getHandler();
            if (handler == null) {
                errorMessages.add("Invalid operation: " + transaction.getOperation());
                continue;
            }
            if (transaction.getFruit() == null) {
                errorMessages.add("Invalid fruit name: null");
                continue;
            }
            if (transaction.getQuantity() <= 0) {
                errorMessages.add("Invalid fruit quantity: "
                        + transaction.getQuantity());
                continue;
            }
            handler.handleOperation(transaction, fruitStorage, errorMessages);
        }
    }

    @Override
    public List<String> generateReport() {
        List<String> reportLines = new ArrayList<>();
        reportLines.add(HEADER_LINE);
        for (Fruit fruit : fruitStorage.getAllFruits()) {
            reportLines.add(fruit.getName() + COMMA + fruit.getQuantity());
        }
        return reportLines;
    }

    @Override
    public List<String> getErrorMessages() {
        return new ArrayList<>(errorMessages);
    }
}
