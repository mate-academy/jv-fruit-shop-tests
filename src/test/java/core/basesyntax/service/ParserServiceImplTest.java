package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.service.impl.WrongFormatException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceImplTest {
    private ParserServiceImpl parserService;
    private FruitTransaction fruitTransaction;
    private List<String> records;
    private List<String> recordsWrong;

    @Before
    public void setUp() {
        parserService = new ParserServiceImpl();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",20);
        records = new ArrayList<>();
        records.add("type,fruit,quantity");
        records.add("b,banana,20");
        records.add("b,apple,100");
        records.add("s,banana,102");
        records.add("p,banana,13");
        records.add("r,apple,10");
        records.add("p,apple,20");
        records.add("p,banana,5");
        records.add("s,banana,50");
        recordsWrong = new ArrayList<>();
        recordsWrong.add("type,fruit,quantity");
        recordsWrong.add("b,banana,yy");
    }

    @Test
    public void parse_rightFile_Ok() {
        List<FruitTransaction> actualResult = parserService.parse(records);
        Assert.assertEquals("Test failed! You should returned next fruit "
                        + fruitTransaction.getFruit() + " but you returned "
                        + actualResult.get(0).getFruit(),
                fruitTransaction, actualResult.get(0));
    }

    @Test(expected = NumberFormatException.class)
    public void parse_wrongFormatInFile_notOk() {
        parserService.parse(recordsWrong);
    }

    @Test(expected = WrongFormatException.class)
    public void parse_emptyFile_notOk() {
        recordsWrong = new ArrayList<>();
        parserService.parse(recordsWrong);
    }
}
