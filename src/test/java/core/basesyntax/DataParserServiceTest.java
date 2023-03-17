package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParserService;
import core.basesyntax.service.impl.DataParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserServiceTest {
    private static List<FruitTransaction> excepted;
    private static List<String> input;
    private static DataParserService parserService;
    private static final String FIRST_FRUIT_NAME = "durian";
    private static final String SECOND_FRUIT_NAME = "papaya";
    private static final int FIRST_QUANTITY = 100;
    private static final int SECOND_QUANTITY = 55;
    private static final int THIRD_QUANTITY = 28;
    private static final int FOURTH_QUANTITY = 45;

    @BeforeClass
    public static void createOutputList() throws Exception {
        parserService = new DataParserServiceImpl();
        excepted = new ArrayList<>();
        FruitTransaction first = new FruitTransaction();
        first.setOperation(FruitTransaction.Operation.BALANCE);
        first.setFruit(FIRST_FRUIT_NAME);
        first.setQuantity(FIRST_QUANTITY);
        excepted.add(first);
        FruitTransaction second = new FruitTransaction();
        second.setOperation(FruitTransaction.Operation.BALANCE);
        second.setFruit(SECOND_FRUIT_NAME);
        second.setQuantity(SECOND_QUANTITY);
        excepted.add(second);
        FruitTransaction third = new FruitTransaction();
        third.setOperation(FruitTransaction.Operation.PURCHASE);
        third.setFruit(FIRST_FRUIT_NAME);
        third.setQuantity(THIRD_QUANTITY);
        excepted.add(third);
        FruitTransaction fourth = new FruitTransaction();
        fourth.setOperation(FruitTransaction.Operation.SUPPLY);
        fourth.setFruit(SECOND_FRUIT_NAME);
        fourth.setQuantity(FOURTH_QUANTITY);
    }

    @Before
    public void createInputList() throws Exception {
        input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("b,durian,100");
        input.add("b,papaya,55");
        input.add("p,durian,28");
        input.add("s,papaya,45");
    }

    @Test
    public void parseData_validInputData_ok() {
        List<FruitTransaction> actual = parserService.parseData(input);
        for (int i = 0; i < excepted.size(); i++) {
            Assert.assertEquals(excepted.get(i), actual.get(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void parseData_invalidOperation_notOk() {
        input.add(1, "m,durian,2003");
        parserService.parseData(input);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void parseData_emptyLines_notOk() {
        input.add(1, "b,durian,");
        parserService.parseData(input);
    }
}
