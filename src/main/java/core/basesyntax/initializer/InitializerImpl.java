package core.basesyntax.initializer;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.untils.FruitTransactionComparator;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InitializerImpl {
    private static final String REGEX = ",";
    private static final int OPERATION = 0;
    private static final int FRUIT_NAME = 1;
    private static final int QUANTITY = 2;
    private final OperationStrategy operationStrategy;

    public InitializerImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    public void initStorage(String filePath) {
        try {
            FruitTransactionComparator fruitTransactionComparator
                    = new FruitTransactionComparator();
            Files.readAllLines(Path.of(getClass()
                            .getClassLoader().getResource(filePath).toURI()))
                    .stream()
                    .skip(1)
                    .map(this::parseTransaction)
                    .sorted(fruitTransactionComparator)
                    .forEach(this::processTransaction);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Can't find file by path" + filePath, e);
        }
    }

    private FruitTransaction parseTransaction(String transactionString) {
        String[] split = transactionString.split(REGEX);
        return new FruitTransaction(FruitTransaction.Operation
                .fromValue(split[OPERATION]), split[FRUIT_NAME], Integer.parseInt(split[QUANTITY]));
    }

    private void processTransaction(FruitTransaction fruitTransaction) {
        operationStrategy.get(fruitTransaction.getOperation())
                .operation(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
