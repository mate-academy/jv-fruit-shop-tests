package core.service.market;

import core.model.FruitRecord;
import java.util.List;

public interface MarketService {
    List applyOperations(List<FruitRecord> fruitRecordList);
}
