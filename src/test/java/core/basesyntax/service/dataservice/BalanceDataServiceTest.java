package core.basesyntax.service.dataservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceDataServiceTest {
    private static DataService dataService;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        FruitDao fruitDao = new FruitDaoImpl();
        dataService = new BalanceDataService(fruitDao);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsStorage.clear();
    }

    @Test
    void processTransaction_incorrectQuantity_notOk() {
        fruitTransaction = new FruitTransaction("b","apple",-1);
        assertThrows(RuntimeException.class,
                () -> dataService.processTransaction(fruitTransaction));
    }

    @Test
    void processTransaction_duplicatedBalance_notOk() {
        Storage.fruitsStorage.put("apple",1);
        fruitTransaction = new FruitTransaction("b","apple",1);
        assertThrows(RuntimeException.class,
                () -> dataService.processTransaction(fruitTransaction));
    }

    @Test
    void processTransaction_correctQuantity_Ok() {
        String fruit = "apple";
        int quantity = 1;
        fruitTransaction = new FruitTransaction("b",fruit,quantity);
        dataService.processTransaction(fruitTransaction);
        assertEquals((int) Storage.fruitsStorage.get(fruit), quantity);
    }
}
