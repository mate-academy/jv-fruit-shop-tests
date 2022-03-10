package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.dto.FruitDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionServiceImplTest {
    private static final String FILE_PATH = "src/main/resources/dataInputTest.csv";
    private static FruitTransactionServiceImpl fruitTransactionService;

    @BeforeClass
    public static void setUp() {
        fruitTransactionService = new FruitTransactionServiceImpl();
    }

    @Test
    public void parse_Info_Ok() {
        List<FruitDto> result = fruitTransactionService.parse("b,banana,20");
        List<FruitDto> expected = new ArrayList<>();
        expected.add(new FruitDto("banana", 20, "b"));
        assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_Null_Ok() {
        fruitTransactionService.parse(null);
    }

    @Test
    public void parse_currentData_Ok() {
        final String s = "b,banana,20\n"
                +
                "b,apple,100\n"
                +
                "s,banana,100\n"
                +
                "p,banana,13\n"
                +
                "r,apple,10\n"
                +
                "p,apple,20\n"
                +
                "p,banana,5\n"
                +
                "s,banana,50\n";
        List<FruitDto> expected = new ArrayList<>();
        expected.add(new FruitDto("banana", 20, "b"));
        expected.add(new FruitDto("apple", 100, "b"));
        expected.add(new FruitDto("banana", 100, "s"));
        expected.add(new FruitDto("banana", 13, "p"));
        expected.add(new FruitDto("apple", 10, "r"));
        expected.add(new FruitDto("apple", 20, "p"));
        expected.add(new FruitDto("banana", 5, "p"));
        expected.add(new FruitDto("banana", 50, "s"));
        assertEquals(expected, fruitTransactionService.parse(s));
    }
}


