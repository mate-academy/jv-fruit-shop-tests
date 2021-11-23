package core.basesyntax.fruitshop.service;

import core.basesyntax.fruitshop.model.RecordDto;
import java.util.List;

public interface FruitShopService {
    void fruitStorageModifier(List<RecordDto> dataInDto);
}
