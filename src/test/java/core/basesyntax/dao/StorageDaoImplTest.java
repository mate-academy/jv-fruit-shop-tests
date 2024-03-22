package core.basesyntax.dao;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StorageDaoImplTest {
    private static StorageDao storageDao = new StorageDaoImpl();
    private static FruitTransactionDto dto = new FruitTransactionDto();
    List<OperationHandler> handlers = List.of();

    @Test
    void add() {
    }

    @Test
    void get() {
    }

    @Test
    void change() {
    }

    @Test
    void testAdd() {
    }

    @Test
    void testGet() {
    }
}