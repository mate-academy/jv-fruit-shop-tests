package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.exeptions.IncorrectDataFileExeption;
import core.basesyntax.exeptions.NegativeNumberExeption;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitDataParserTest {
    private FruitDataParser fruitDataParser;

    @BeforeEach
    public void setUp() {
        fruitDataParser = new FruitDataParser();
    }

    @Test
    public void parse_ValidData_ReturnsListOfDto() {
        List<String> data = List.of("type,fruit,quantity",
                "s,apple,20",
                "s,banana,30");
        List<FruitTransactionDto> result = fruitDataParser.parse(data);
        FruitTransactionDto firstDto = new FruitTransactionDto("s", "apple", 20);
        FruitTransactionDto secondDto = new FruitTransactionDto("s", "banana", 30);

        assertEquals(2, result.size());
        assertEquals(firstDto, result.get(0));
        assertEquals(secondDto, result.get(1));
    }

    @Test
    public void parse_IncorrectDataFileExceptionThrown() {
        List<String> data = List.of("type,fruit,quantity", "p,banana");
        assertThrows(IncorrectDataFileExeption.class, () -> fruitDataParser.parse(data));
    }

    @Test
    public void parse_NegativeNumberExceptionThrown() {
        List<String> data = List.of("type,fruit,quantity", "r,apple,-10");
        assertThrows(NegativeNumberExeption.class, () -> fruitDataParser.parse(data));
    }

    @Test
    public void parse_EmptyData_ReturnsEmptyList() {
        List<String> data = new ArrayList<>();
        assertThrows(IncorrectDataFileExeption.class, () -> fruitDataParser.parse(data));
    }
}
