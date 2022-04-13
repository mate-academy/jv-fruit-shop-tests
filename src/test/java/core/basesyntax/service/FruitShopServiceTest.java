package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.OperationException;
import core.basesyntax.exceptions.QuantityException;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitShopServiceTest {
    private static FruitShopService service;
    private static final String INPUT_FILE = "src/test/resources/InputData.csv";
    private static final String OUTPUT_FILE = "src/test/resources/OutputData.csv";
    private static final String WRONG_OPERATION_FILE = "src/test/resources/WrongOperation.csv";
    private static final String WRONG_QUANTITY_FILE = "src/test/resources/WrongQuantity.csv";

    @BeforeAll
    static void beforeAll() {
        service = new FruitShopServiceImpl();
    }

    @AfterEach
    void tearDown() throws FileNotFoundException {
        Storage.fruits.clear();
        PrintWriter pw = new PrintWriter(OUTPUT_FILE);
        pw.close();
    }

    @Test
    public void update_properData_Ok() {
        service.update(INPUT_FILE, OUTPUT_FILE);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 152);
        expected.put("apple", 90);
        Map<String, Integer> actual = Storage.fruits;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void update_wrongOperation_notOk() {
        Assertions.assertThrows(OperationException.class,
                () -> service.update(WRONG_OPERATION_FILE, OUTPUT_FILE));
    }

    @Test
    void update_wrongQuantity_notOk() {
        Assertions.assertThrows(QuantityException.class,
                () -> service.update(WRONG_QUANTITY_FILE, OUTPUT_FILE));
    }
}
