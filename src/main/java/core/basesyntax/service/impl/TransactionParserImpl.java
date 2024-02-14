package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.TransactionService;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionParserImpl implements TransactionParser {
    private final TransactionService transactionService;

    public TransactionParserImpl(TransactionService transactionService) {
        if (transactionService == null) {
            throw new IllegalArgumentException("Constructor parameter can't be null");
        }
        this.transactionService = transactionService;
    }

    @Override
    public List<FruitTransaction> parse(List<String> lines) {
        if (lines == null) {
            throw new IllegalArgumentException("Parameter can't be null");
        }
        if (lines.isEmpty()) {
            throw new RuntimeException("List '" + lines + "' is empty");
        }
        return lines.stream()
                .map(transactionService::createTransaction)
                .collect(Collectors.toList());
    }
}
