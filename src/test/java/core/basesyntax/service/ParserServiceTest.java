package core.basesyntax.service;

import core.basesyntax.exception.ValidatorServiceException;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.impl.CsvParserServiceImpl;
import core.basesyntax.service.impl.ValidatorServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceTest {
    private static ValidatorService validator;
    private static ParserService<TransactionDto> csvParcer;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorServiceImpl();
        csvParcer = new CsvParserServiceImpl(validator);
    }

    @Test
    public void parseLines_validList_ok() {
        List<String> dataFromFile = new ArrayList();
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("b,banana,20");
        dataFromFile.add("b,apple,100");
        dataFromFile.add("s,banana,100");
        dataFromFile.add("p,banana,13");
        List<TransactionDto> expected = new ArrayList<>();
        expected.add(new TransactionDto("b", "banana", 20));
        expected.add(new TransactionDto("b", "apple", 100));
        expected.add(new TransactionDto("s", "banana", 100));
        expected.add(new TransactionDto("p", "banana", 13));
        List<TransactionDto> actual = csvParcer.parseLines(dataFromFile);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void parseLines_nullList_notOk() {
        csvParcer.parseLines(null);
    }

    @Test(expected = ValidatorServiceException.class)
    public void parseLines_invalidLine_notOk() {
        List<String> dataFromFile = new ArrayList<>();
        dataFromFile.add("B");
        dataFromFile.add("BANANA");
        dataFromFile.add("-100");
        csvParcer.parseLines(dataFromFile);
    }
}
