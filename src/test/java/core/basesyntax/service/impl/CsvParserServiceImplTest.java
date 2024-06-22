package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CsvParserService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvParserServiceImplTest {
    private final CsvParserService csvParserService = new CsvParserServiceImpl();

    @Test
    void parse_validData_success() {
        List<String> lines = Arrays.asList("type,fruit,quantity", "b,apple,10", "s,banana,20");
        List<FruitTransaction> transactions = csvParserService.parse(lines);
        assertEquals(2, transactions.size());
    }

    @Test
    void parse_invalidData_throwsException() {
        List<String> lines = Arrays.asList("type,fruit,quantity", "invalid,data,line");
        assertThrows(IllegalArgumentException.class, () -> csvParserService.parse(lines));
    }
}
