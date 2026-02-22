package core.basesyntax.handler;

import core.basesyntax.FruitTransaction;
import java.util.Map;

public interface OperationHandler {
    public void handle(FruitTransaction transaction, Map<String, Integer> storage);
}
