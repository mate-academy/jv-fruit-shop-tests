package core.basesyntax.service;

import core.basesyntax.service.strategy.Handler;

public interface OperationStrategy {
    Handler get(String[] str);
}
