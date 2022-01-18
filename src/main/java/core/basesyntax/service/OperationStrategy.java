package core.basesyntax.service;

import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.service.operationhandler.OperationHandler;

public interface OperationStrategy {

    OperationHandler getOperationHandler(OperationTypes operationType) throws ValidationException;

}
