package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataParserServiceImplTest {
    private DataParserServiceImpl dataParserService;

    @BeforeEach
    void setUp() {
        dataParserService = new DataParserServiceImpl();
    }

    @DisplayName("Checking for passing null as a lines value")
    @Test
    void parseDate_nullLinesValue_notOk() {
        assertThrows(NullPointerException.class, () -> dataParserService.parseData(null));
    }

    @DisplayName("Checking for passing empty list")
    @Test
    void parseData_emptyLinesList_ok() {
        List<FruitTransaction> expected = Collections.emptyList();
        List<FruitTransaction> actual = dataParserService.parseData(Collections.emptyList());
        assertEquals(expected, actual);
    }

    @DisplayName("Checking for passing correct data lines")
    @Test
    void parseData_correctLinesData_ok() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,banana,40",
                "s,apple,30",
                "p,kiwi,20",
                "r,pineapple,10"
        );
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 40),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 30),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "kiwi", 20),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "pineapple", 10)
        );
        List<FruitTransaction> actual = dataParserService.parseData(lines);
        assertEquals(expected, actual);
    }
}
