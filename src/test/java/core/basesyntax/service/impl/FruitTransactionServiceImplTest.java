package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.dataservice.BalanceDataService;
import core.basesyntax.service.dataservice.DataService;
import core.basesyntax.strategy.DataServiceStrategy;
import core.basesyntax.strategy.DataServiceStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    private static FruitTransactionService fruitTransactionService;
    private static List<FruitTransaction> expectedFruitTransactionList;

    @BeforeEach
    void setUp() {
        DataService dataService = new BalanceDataService(new FruitDaoImpl());
        Map<FruitTransaction.Operation, DataService> dataServiceMap = new HashMap<>();
        dataServiceMap.put(FruitTransaction.Operation.BALANCE, dataService);
        DataServiceStrategy dataServiceStrategy = new DataServiceStrategyImpl(dataServiceMap);
        fruitTransactionService = new FruitTransactionServiceImpl(dataServiceStrategy);
        expectedFruitTransactionList = new ArrayList<>();
        expectedFruitTransactionList.add(new FruitTransaction("b","banana",20));
    }

    @Test
    void parseTransactions_correctData_Ok() {
        List<String> sourceContent = new ArrayList<>();
        sourceContent.add("this string shouldn't be parsed");
        sourceContent.add(expectedFruitTransactionList.get(0).getOperation().getCode()
                        + "," + expectedFruitTransactionList.get(0).getFruitName()
                        + "," + expectedFruitTransactionList.get(0).getQuantity());
        assertEquals(expectedFruitTransactionList.get(0).toString(),
                fruitTransactionService.parseTransactions(sourceContent).get(0).toString());
    }

    @Test
    void processTransactions_correctData_Ok() {
        assertDoesNotThrow(() ->
                fruitTransactionService.processTransactions(expectedFruitTransactionList));
    }

    @AfterAll
    static void afterAll() {
        Storage.fruitsStorage.clear();
    }
}
