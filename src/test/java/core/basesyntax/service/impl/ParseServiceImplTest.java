package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class ParseServiceImplTest {
    private static final List<String> ONLY_HEADER_DATABASE = new ArrayList<>(
            List.of("type,fruit,quantity"));
    private static final List<String> NORMAL_DATABASE = new ArrayList<>(
            List.of("type,fruit,quantity",
                    "b,banana,20",
                    "b,apple,100",
                    "s,banana,100",
                    "p,banana,13",
                    "r,apple,10",
                    "p,apple,20",
                    "p,banana,5",
                    "s,banana,50"));
    private static final List<String> CORRUPTED_DATABASE = new ArrayList<>(
            List.of("type,fruit,quantity",
                    "b,banana,20",
                    "u,apple,100q",
                    "s,banana,100",
                    "p,banana,13",
                    "q,apple,10q",
                    "G,apple,20q",
                    "p,banana,5",
                    "s,banana,50"));
    private final ParseService parseService = new ParseServiceImpl();

    @Test
    public void parse_emptyDatabase_ok() {
        assertEquals(Collections.emptyList(), parseService.parse(Collections.emptyList()));
    }

    @Test
    public void parse_onlyHeaderDatabase_ok() {
        assertEquals(Collections.emptyList(), parseService.parse(ONLY_HEADER_DATABASE));
    }

    @Test
    public void parse_normalDatabase_ok() {
        FruitTransaction expected = new FruitTransaction();
        expected.setOperation(FruitTransaction.Operation.RETURN);
        expected.setFruit("apple");
        expected.setQuantity(10);
        FruitTransaction actual = parseService.parse(NORMAL_DATABASE).get(4);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_corruptedDatabase_ok() {
        FruitTransaction expected = new FruitTransaction();
        expected.setOperation(FruitTransaction.Operation.SUPPLY);
        expected.setFruit("banana");
        expected.setQuantity(50);
        FruitTransaction actual = parseService.parse(CORRUPTED_DATABASE).get(4);
        assertEquals(expected, actual);
    }
}
