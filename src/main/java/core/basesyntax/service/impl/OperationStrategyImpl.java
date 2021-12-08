package core.basesyntax.service.impl;

import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.strategy.BalanceHandler;
import core.basesyntax.service.strategy.Handler;
import core.basesyntax.service.strategy.PurchaseHandler;
import core.basesyntax.service.strategy.ReturnHandler;
import core.basesyntax.service.strategy.SupplyHandler;
import java.util.HashMap;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<String, Handler> map = new HashMap<>() {{
            put("b", new BalanceHandler());
            put("s", new SupplyHandler());
            put("p", new PurchaseHandler());
            put("r", new ReturnHandler());
        }};

    @Override
    public Handler get(String[] str) {
        return map.get(str[0]);
    }
}
