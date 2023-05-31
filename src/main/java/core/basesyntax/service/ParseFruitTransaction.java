package core.basesyntax.service;

import core.basesyntax.service.operation.OperationStrategy;
import java.util.List;

public interface ParseFruitTransaction {
    void parse(List<String> inputData, OperationStrategy operation);
}
