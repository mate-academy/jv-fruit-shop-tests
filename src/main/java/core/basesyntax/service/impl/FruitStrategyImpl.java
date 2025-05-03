package core.basesyntax.service.impl;

import core.basesyntax.service.FruitStrategy;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.FruitOperation;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.Map;

public class FruitStrategyImpl implements FruitStrategy {

    private static final Map<String, FruitOperation> OPERATION_MAP = new HashMap<>();
    private static final String BALANCE;
    private static final String PURCHASE;
    private static final String RETURN;
    private static final String SUPPLY;

    static {
        BALANCE = "b";
        PURCHASE = "p";
        RETURN = "r";
        SUPPLY = "s";
        OPERATION_MAP.put(BALANCE, new BalanceOperation());
        OPERATION_MAP.put(PURCHASE, new PurchaseOperation());
        OPERATION_MAP.put(RETURN, new ReturnOperation());
        OPERATION_MAP.put(SUPPLY, new SupplyOperation());
    }

    @Override
    public FruitOperation makeOperation(String abbreviation) {
        return OPERATION_MAP.entrySet()
                .stream()
                .filter(s -> s.getKey().equals(abbreviation))
                .map(s -> s.getValue())
                .findFirst().orElseThrow(
                        () -> new RuntimeException("Wrong abbreviation of operation"));
    }
}
