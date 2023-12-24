package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceTest {
    private static final String APPLE = "apple";
    private static final String MANGO = "mango";
    private static ParserService parserService;
    private static List<String> preParsingData;
    private static List<FruitTransaction> postParsingData;

    @BeforeAll
    static void beforeAll() {
        parserService = new ParserServiceImpl();
        postParsingData =
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 10),
                        new FruitTransaction(FruitTransaction.Operation.BALANCE, MANGO, 3),
                        new FruitTransaction(FruitTransaction.Operation.SUPPLY, MANGO, 2));
    }

    @BeforeEach
    void setUp() {
        preParsingData = new ArrayList<>(List.of("type,fruit,quantity",
                                                    "b,apple,10",
                                                    "b,mango,3",
                                                    "s,mango,2"));
    }

    @Test
    void parseData_validData_ok() {
        List<FruitTransaction> actual = parserService.parseData(preParsingData);
        assertEquals(postParsingData, actual);
    }

    @Test
    void parseData_invalidData_notOk() {
        preParsingData.add("f,apple");
        assertThrows(IllegalArgumentException.class,
                () -> parserService.parseData(preParsingData));
        assertThrows(NullPointerException.class, () -> parserService.parseData(null));
    }

    @Test
    void parseData_invalidFruitQuantity_notOk() {
        preParsingData.add("s,apple,-3");
        assertThrows(RuntimeException.class, () -> parserService.parseData(preParsingData));
    }
}
