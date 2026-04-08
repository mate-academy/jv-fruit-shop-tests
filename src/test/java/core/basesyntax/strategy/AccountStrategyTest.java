package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.strategy.impl.AccountStrategyImpl;
import core.basesyntax.strategy.impl.SupplyAccountHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AccountStrategyTest {
    @Test
    void get_validType_returnsHandler() {
        Map<AccountType, AccountHandler> map = new HashMap<>();
        AccountHandler handler = new SupplyAccountHandler();
        map.put(AccountType.SUPPLY, handler);

        AccountStrategy strategy = new AccountStrategyImpl(map);

        assertEquals(handler, strategy.get(AccountType.SUPPLY));
    }
}
