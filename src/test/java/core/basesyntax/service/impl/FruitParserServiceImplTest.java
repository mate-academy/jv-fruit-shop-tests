package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitParserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FruitParserServiceImplTest {

    @Test
    public void parser_vaildInput_Ok() {
        String line1 = "type,fruit,quantity";
        String line2 = "b,banana,152";
        String line3 = "s,apple,90";
        List<String> list = new ArrayList<>();
        list.add(line1);
        list.add(line2);
        list.add(line3);
        FruitParserService parserService = new FruitParserServiceImpl();
        List<FruitTransaction> fruitTransactions =
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 152),
                        new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 90));
        assertEquals(fruitTransactions, parserService.parserFruitTransaction(list));
    }

    @Test
    public void parser_NotvaildInput_notOk() {
        FruitParserService fruitParserService = new FruitParserServiceImpl();
        List<String> list = Arrays.asList("type,fruit,quantity");
        assertThrows(RuntimeException.class, () -> fruitParserService.parserFruitTransaction(list),
                "Not valid length data ");
    }
}
