package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.BalanceStorageDao;
import core.basesyntax.db.impl.InMemoryBalanceStorageDao;
import core.basesyntax.dto.ActivityDto;
import core.basesyntax.enums.ActivityType;
import core.basesyntax.exception.NegativeQuantityException;
import core.basesyntax.model.Product;
import core.basesyntax.service.BalanceUpdater;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceUpdaterImplTest {
    private final BalanceStorageDao balanceStorage = InMemoryBalanceStorageDao.getInstance();
    private final BalanceUpdater balanceUpdater = new BalanceUpdaterImpl(balanceStorage);

    @BeforeEach
    void setUp() {
        balanceStorage.clear();
    }

    @Test
    void updateFrom_Ok() {
        List<ActivityDto> activities = List.of(
                new ActivityDto(ActivityType.BALANCE, "banana", 20),
                new ActivityDto(ActivityType.SUPPLY, "apple", 100),
                new ActivityDto(ActivityType.PURCHASE, "banana", 13),
                new ActivityDto(ActivityType.RETURN, "apple", 10)
        );
        balanceUpdater.updateFrom(activities);
        assertEquals(7, balanceStorage.getQuantity(new Product("banana")));
        assertEquals(110, balanceStorage.getQuantity(new Product("apple")));
    }

    @Test
    void updateFrom_negative_notOk() {
        List<ActivityDto> activities = List.of(
                new ActivityDto(ActivityType.BALANCE, "banana", 20),
                new ActivityDto(ActivityType.PURCHASE, "banana", 21)
        );
        NegativeQuantityException existsException = assertThrows(
                NegativeQuantityException.class,
                () -> balanceUpdater.updateFrom(activities)
        );
        assertEquals("Received negative quantity: -1",
                existsException.getMessage());
    }
}
