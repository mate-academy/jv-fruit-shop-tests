package core.basesyntax.operation;

import core.basesyntax.model.Record;

public interface OperationHandler {
    void apply(Record record);
}
