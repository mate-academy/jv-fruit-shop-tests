package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportService;
import core.basesyntax.strategy.TransactionStrategy;
import java.util.List;

public class ReportServiceImpl implements ReportService {
    private static final String HEADLINE_OF_OUTPUT_FILE = "fruit,quantity";
    private final TransactionStrategy transactionStrategy;

    public ReportServiceImpl(TransactionStrategy transactionStrategy) {
        this.transactionStrategy = transactionStrategy;
    }

    @Override
    public String countAmountOfFruits(List<FruitTransaction> fruitTransactions) {
        fruitTransactions
                .forEach(transaction -> transactionStrategy.get(transaction.getOperation())
                        .doTransaction(transaction.getFruit(), transaction.getQuantity()));
        StringBuilder reportInfoBuilder = new StringBuilder(HEADLINE_OF_OUTPUT_FILE
                + System.lineSeparator());
        Storage.fruits.forEach(fruit -> reportInfoBuilder.append(fruit.toString()));
        return reportInfoBuilder.toString();
    }
}
