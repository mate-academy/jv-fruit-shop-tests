package com.basesyntax.services;

import com.basesyntax.strategy.Strategy;
import java.util.List;

public interface TransactionProcessor {
    void processingData(List<String> data, Strategy strategy);
}
