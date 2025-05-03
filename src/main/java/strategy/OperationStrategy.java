package strategy;

import core.basesyntax.model.ParsedLine;
import service.OperationService;

public interface OperationStrategy {
    OperationService getOperationService(ParsedLine line);
}
