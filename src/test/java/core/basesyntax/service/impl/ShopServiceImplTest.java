package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.GoodsDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.AccountHandler;
import core.basesyntax.strategy.AccountStrategy;
import core.basesyntax.strategy.AccountType;
import core.basesyntax.strategy.impl.AccountStrategyImpl;
import core.basesyntax.strategy.impl.BalanceAccountHandler;
import core.basesyntax.strategy.impl.PurchaseAccountHandler;
import core.basesyntax.strategy.impl.ReturnAccountHandler;
import core.basesyntax.strategy.impl.SupplyAccountHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService service;

    @BeforeEach
    void setUp() {
        GoodsDao dao = new FruitDaoImpl();

        Map<AccountType, AccountHandler> map = Map.of(
                AccountType.BALANCE, new BalanceAccountHandler(),
                AccountType.SUPPLY, new SupplyAccountHandler(),
                AccountType.PURCHASE, new PurchaseAccountHandler(),
                AccountType.RETURN, new ReturnAccountHandler()
        );

        AccountStrategy strategy = new AccountStrategyImpl(map);

        service = new ShopServiceImpl(dao, strategy);
    }

    @AfterEach
    void reset() {
        Storage.getFruits().clear();
    }

    @Test
    void process_validTransactions_ok() {
        List<String[]> input = List.of(
                new String[]{"b", "apple", "10"},
                new String[]{"s", "apple", "5"},
                new String[]{"p", "apple", "3"}
        );

        List<String[]> result = service.process(input);

        assertEquals(1, result.size());
        assertEquals("apple", result.get(0)[0]);
        assertEquals("12", result.get(0)[1]);
    }

    @Test
    void process_multipleFruits_ok() {
        List<String[]> input = List.of(
                new String[]{"b", "apple", "10"},
                new String[]{"b", "banana", "20"}
        );

        List<String[]> result = service.process(input);

        assertEquals(2, result.size());
    }

    @Test
    void process_purchaseTooMuch_throwsException() {
        List<String[]> input = List.of(
                new String[]{"b", "apple", "5"},
                new String[]{"p", "apple", "10"}
        );

        assertThrows(RuntimeException.class,
                () -> service.process(input));
    }
}
