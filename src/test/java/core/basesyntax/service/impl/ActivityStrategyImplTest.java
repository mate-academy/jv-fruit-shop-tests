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
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ActivityStrategyImplTest {
    private static ActivityStrategyImpl activityStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitActivity.Type, ActivityHandler> activityHandlerMap = Map.of(
                FruitActivity.Type.BALANCE, new BalanceHandler(),
                FruitActivity.Type.SUPPLY, new SupplyHandler(),
                FruitActivity.Type.PURCHASE, new PurchaseHandler(),
                FruitActivity.Type.RETURN, new ReturnHandler());
        activityStrategy = new ActivityStrategyImpl(activityHandlerMap);
    }

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
