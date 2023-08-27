package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeAll
    static void setup() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parse_successful() {
        List<String> input = List.of("operation,fruit,quantity", "b,apple,10");
        List<FruitTransaction> transactionList = parserService.parse(input);
        assertEquals(input.get(1).split(",")[0],
                transactionList.get(0).getOperation().getCode());
        assertEquals(input.get(1).split(",")[1],
                transactionList.get(0).getFruit());
        assertEquals(input.get(1).split(",")[2],
                String.valueOf(transactionList.get(0).getQuantity()));
    }

    @Test
    void parse_emptyInput_NotOK() {
        List<String> emptyInput = new ArrayList<>();
        assertThrows(RuntimeException.class, () -> {
            parserService.parse(emptyInput);
        });
    }
}
