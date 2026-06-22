package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FruitServiceImpl implements FruitService {
    private final OperationStrategy operationStrategy;

    public FruitServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processTransactions(String inputFilePath) {
        try {
            List<FruitTransaction> transactions = Files.lines(Paths.get(inputFilePath))
                    .skip(1) // Skip header
                    .map(this::parseTransaction)
                    .collect(Collectors.toList());

            for (FruitTransaction transaction : transactions) {
                OperationHandler handler = operationStrategy.get(transaction.getOperation());
                handler.apply(transaction);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read input file: " + inputFilePath, e);
        }
    }

    private FruitTransaction parseTransaction(String line) {
        String[] parts = line.split(",");
        FruitTransaction.Operation operation = FruitTransaction.Operation.fromCode(parts[0]);
        String fruit = parts[1];
        int quantity = Integer.parseInt(parts[2]);
        return new FruitTransaction(operation, fruit, quantity);
    }
}
