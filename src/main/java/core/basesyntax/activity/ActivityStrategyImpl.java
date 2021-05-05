package core.basesyntax.activity;

import core.basesyntax.service.handler.FruitHandler;
import java.util.Map;

public class ActivityStrategyImpl implements ActivityStrategy {
    private final Map<Activities, FruitHandler> handleGoodsMap;

    public ActivityStrategyImpl(Map<Activities, FruitHandler> handleGoodsMap) {
        this.handleGoodsMap = handleGoodsMap;
    }

    @Override
    public FruitHandler get(Activities activities) {
        return handleGoodsMap.get(activities);
    }

}
