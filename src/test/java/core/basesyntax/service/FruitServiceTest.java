package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.service.amount.ActivityHandler;
import core.basesyntax.service.amount.BalanceActivityHandler;
import core.basesyntax.service.amount.PurchaseActivityHandler;
import core.basesyntax.service.amount.ReturnActivityHandler;
import core.basesyntax.service.amount.SupplyActivityHandler;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.TypeActivityStrategy;
import core.basesyntax.strategy.TypeActivityStrategyImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceTest {
    private static final String FROM_FILE = "file.CSV";
    private static final String TO_FILE = "newFile.CSV";
    private static Map<Operation, ActivityHandler> activityHandlerMap;

    @BeforeAll
    static void beforeAll() {
        activityHandlerMap = new HashMap<>();

        activityHandlerMap.put(Operation.BALANCE,
                new BalanceActivityHandler(new FruitTransactionDaoImpl()));

        activityHandlerMap.put(Operation.SUPPLY,
                new SupplyActivityHandler(new FruitTransactionDaoImpl()));

        activityHandlerMap.put(Operation.PURCHASE,
                new PurchaseActivityHandler(new FruitTransactionDaoImpl()));

        activityHandlerMap.put(Operation.RETURN,
                new ReturnActivityHandler(new FruitTransactionDaoImpl()));
    }

    @Test
    void writeReport_isOk() {
        TypeActivityStrategy typeActivityStrategy
                = new TypeActivityStrategyImpl(activityHandlerMap);

        ReaderService readerService
                = new ReaderServiceImpl(new ParserServiceImpl());

        FruitService fruitService =
                new FruitServiceImpl(readerService, new WriterServiceImpl(), typeActivityStrategy);

        fruitService.writeReport(FROM_FILE);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(TO_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + TO_FILE);
        }
        List<String> expected = List.of("fruit, quantity", "apple,70", "banana,35");
        assertIterableEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        Storage.fruitTransactions.clear();
    }
}
