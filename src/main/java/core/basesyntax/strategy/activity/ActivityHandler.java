package core.basesyntax.strategy.activity;

import core.basesyntax.model.Record;

public interface ActivityHandler {
    void apply(Record record);
}
