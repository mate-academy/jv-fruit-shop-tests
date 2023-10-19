package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.amount.ActivityHandler;
import core.basesyntax.service.amount.BalanceActivityHandler;
import core.basesyntax.service.amount.SupplyActivityHandler;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.TypeActivityStrategy;
import core.basesyntax.strategy.TypeActivityStrategyImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FruitServiceTest {
    private static FruitService fruitService;
    private static final String EXIST_FILE = "file.CSV";
    private static final String EXIST_FILE_2 = "file2.CSV";
    private static final String NON_EXIST_FILE = "newFile.CSV";
    private static Map<FruitTransaction.Operation, ActivityHandler> activityHandlerMap;
    private static TypeActivityStrategy typeActivityStrategy;

    @BeforeAll
    static void beforeAll() {
        activityHandlerMap = new HashMap<>();
        activityHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceActivityHandler());
        activityHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyActivityHandler());
        typeActivityStrategy = new TypeActivityStrategyImpl(activityHandlerMap);
        fruitService = new FruitServiceImpl(new ReaderServiceImpl(),
                new WriterServiceImpl(), typeActivityStrategy);
    }

    @Test
    void getReport_is0k() {

    }

    @Test
    void getReportWithSupply_isOk() {

    }

    @Test
    void writeReportToNonExistFile_isNotOk() {

    }
}