package core.basesyntax.service.impl;

import core.basesyntax.model.dto.FruitDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    private static final String filePath = "src/main/resources/dataInputTest.csv";
    private FruitTransactionServiceImpl fruitTransactionService;
    private List<FruitDto> result = new ArrayList<>();

    @BeforeEach
    void setUp() {
        fruitTransactionService = new FruitTransactionServiceImpl();
    }

    @Test
    public void parseInfo_Ok() {
        List<FruitDto> result = fruitTransactionService.parser("b,banana,20");
        List<FruitDto> expected = new ArrayList<>();
        expected.add(new FruitDto("banana", 20, "b"));
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void parsingNullOrEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            fruitTransactionService.parser(null);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            fruitTransactionService.parser("");
        });
    }

    @Test
    public void parsingCurrentDataInput() {
        FruitTransactionServiceImpl fruitTransactionService = new FruitTransactionServiceImpl();
        String dataFromFile = "hard code";
        result = fruitTransactionService.parser(dataFromFile);
        List<FruitDto> expected = new ArrayList<>();
        expected.add(new FruitDto("banana", 20, "b"));
        expected.add(new FruitDto("apple", 100, "b"));
        expected.add(new FruitDto("banana", 100, "s"));
        expected.add(new FruitDto("banana", 13, "p"));
        expected.add(new FruitDto("apple", 10, "r"));
        expected.add(new FruitDto("apple", 20, "p"));
        expected.add(new FruitDto("banana", 5, "p"));
        expected.add(new FruitDto("banana", 50, "s"));
        Assertions.assertEquals(expected, result);
    }
}
