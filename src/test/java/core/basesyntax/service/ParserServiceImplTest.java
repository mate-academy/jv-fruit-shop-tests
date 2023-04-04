package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.service.impl.WrongFormatException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParserServiceImplTest {
    private ParserServiceImpl parserService;
    private FruitTransaction fruitTransaction;
    private List<String> records;
    private List<String> recordsWrong;

    @BeforeEach
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
        Assertions.assertEquals(fruitTransaction, actualResult.get(0));
    }

    @Test
    public void parse_wrongFormatInFile_notOk() {
        Assertions.assertThrows(NumberFormatException.class, () ->
                parserService.parse(recordsWrong));
    }

    @Test
    public void parse_emptyFile_notOk() {
        recordsWrong = new ArrayList<>();
        Assertions.assertThrows(WrongFormatException.class, () ->
                parserService.parse(recordsWrong));
    }
}
