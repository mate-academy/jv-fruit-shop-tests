package core.basesyntax.service.impl;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import core.basesyntax.model.FruitActivity;
import core.basesyntax.service.activity.strategy.ActivityHandler;
import core.basesyntax.service.activity.strategy.BalanceHandler;
import core.basesyntax.service.activity.strategy.PurchaseHandler;
import core.basesyntax.service.activity.strategy.ReturnHandler;
import core.basesyntax.service.activity.strategy.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ActivityStrategyImplTest {
    private static final Map<FruitActivity.Type, ActivityHandler> ACTIVITY_HANDLER_MAP
            = new HashMap<>();

    static {
        ACTIVITY_HANDLER_MAP.put(FruitActivity.Type.BALANCE, new BalanceHandler());
        ACTIVITY_HANDLER_MAP.put(FruitActivity.Type.SUPPLY, new SupplyHandler());
        ACTIVITY_HANDLER_MAP.put(FruitActivity.Type.PURCHASE, new PurchaseHandler());
        ACTIVITY_HANDLER_MAP.put(FruitActivity.Type.RETURN, new ReturnHandler());
    }

    private static final ActivityStrategyImpl activityStrategy
            = new ActivityStrategyImpl(ACTIVITY_HANDLER_MAP);

    @ParameterizedTest
    @MethodSource("validTypes")
    void get(FruitActivity.Type type, Class clas) {
        assertThat(activityStrategy.get(type), instanceOf(clas));
    }

    static Stream<Arguments> validTypes() {
        return Stream.of(
                arguments(FruitActivity.Type.BALANCE, BalanceHandler.class),
                arguments(FruitActivity.Type.PURCHASE, PurchaseHandler.class),
                arguments(FruitActivity.Type.RETURN, ReturnHandler.class),
                arguments(FruitActivity.Type.SUPPLY, SupplyHandler.class)
        );
    }
}
