package core.basesyntax.service;

import core.basesyntax.model.FruitRecordDto;
import java.util.List;

public interface StoreService {
    void doInstruction(List<FruitRecordDto> info);
}
