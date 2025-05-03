package core.basesyntax.service;

import core.basesyntax.service.calculation.TransactionCalculation;

public interface TransactionStrategy {
    TransactionCalculation get(String operation);
}
