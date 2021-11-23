package core.basesyntax.service.activities;

import core.basesyntax.fruitrecord.FruitRecord;

public interface ActivityHandler {
    void apply(FruitRecord record);
}
