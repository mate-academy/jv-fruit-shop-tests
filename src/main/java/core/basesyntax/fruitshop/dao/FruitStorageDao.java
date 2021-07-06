package core.basesyntax.fruitshop.dao;

import core.basesyntax.fruitshop.model.Fruit;
import core.basesyntax.fruitshop.model.dto.FruitOperationDto;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public interface FruitStorageDao {
    BigDecimal getValueFromStorage(FruitOperationDto fruitOperationDto);

    void updateDataInStorage(FruitOperationDto fruitOperationDto);

    Set<Map.Entry<Fruit, BigDecimal>> getDataReportFromStorage();
}
