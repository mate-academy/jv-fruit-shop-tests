package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ScvParseServiceImplTest {
    private final ParserService parserService = new ScvParseServiceImpl();

    @Test
    public void parse_correctParse_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));

        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,20");
        data.add("b,apple,100");
        List<FruitTransaction> actual = parserService.parse(data);

        for (int i = 0; i < actual.size(); i++) {
            FruitTransaction expectedTransaction = expected.get(i);
            FruitTransaction actualTransaction = actual.get(i);
            assertEquals("Test fail! Expected transaction type: "
                    + expectedTransaction.getOperation() + ", actual type: "
                    + actualTransaction.getOperation(),
                    expectedTransaction.getOperation(), actualTransaction.getOperation());
            assertEquals("Test fail! Expected fruit: "
                    + expectedTransaction.getFruit() + ", actual fruit: "
                    + actualTransaction.getFruit(),
                    expectedTransaction.getFruit(), actualTransaction.getFruit());
            assertEquals("Test fail! Expected quantity: "
                    + expectedTransaction.getQuantity() + ", actual quantity: "
                    + actualTransaction.getQuantity(),
                    expectedTransaction.getQuantity(), actualTransaction.getQuantity());
        }
    }

    @Test(expected = RuntimeException.class)
    public void parse_nullData_notOk() {
        parserService.parse(null);
    }
}
