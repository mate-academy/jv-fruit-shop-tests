package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseDataService;
import core.basesyntax.service.impl.ParseDataServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParseDataServiceImplTest {
    ParseDataService parseDataService;
    @BeforeEach
    void setUp() {
        parseDataService = new ParseDataServiceImpl();
    }

    @Test
    void parseData_correctData_ok() {
        List<FruitTransaction> listExpected = new ArrayList<>();
        listExpected.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        listExpected.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        String strForParse = "type,fruit,quantity\nb,banana,20\nb,apple,100";
        assertEquals(listExpected, parseDataService.parseData(strForParse));
    }

    @Test
    void parseData_dataNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> parseDataService.parseData(null));
    }

    @Test
    void parseData_dataOnlyTitle_notOk() {
        assertThrows(RuntimeException.class, () -> parseDataService.parseData("type,fruit,quantity"));
    }

    @Test
    void parseData_incorrectData_notOk() {
        assertThrows(RuntimeException.class,
                () -> parseDataService.parseData("type,fruit,quantity\nb,banana"));
        assertThrows(RuntimeException.class,
                () -> parseDataService.parseData("type,fruit,quantity\nb,banana,10,20"));
    }

    @Test
    void parseData_withoutNameFruit_notOk() {
        assertThrows(RuntimeException.class,
                () -> parseDataService.parseData("type,fruit,quantity\nb,,10"));
    }

    @Test
    void parseData_withoutQuantityFruit_notOk() {
        assertThrows(RuntimeException.class,
                () -> parseDataService.parseData("type,fruit,quantity\nb,banana,"));
    }

    @Test
    void parseData_withoutOperationFruit_notOk() {
        assertThrows(RuntimeException.class,
                () -> parseDataService.parseData("type,fruit,quantity\n,banana,10"));
    }

    @Test
    void parseData_incorrectOperationFruit_notOk() {
        assertThrows(RuntimeException.class,
                () -> parseDataService.parseData("type,fruit,quantity\na,banana,10"));
    }
}
