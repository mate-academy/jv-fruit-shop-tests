package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.exeptions.IncorrectDataFileExeption;
import core.basesyntax.exeptions.NegativeNumberExeption;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FruitDataParserTest {
    public static final FruitTransactionDto firstDto =
            new FruitTransactionDto("s", "apple", 20);
    public static final FruitTransactionDto secondDto =
            new FruitTransactionDto("s", "banana", 30);
    public static final int NUMBER_OF_ELEMENTS = 2;
    public static final int FIRST_ELEMENT_INDEX = 0;
    public static final int SECOND_ELEMENT_INDEX = 1;
    private FruitDataParser fruitDataParser = new FruitDataParser();

    @Test
    public void parse_ValidData_ReturnsListOfDto() {
        List<String> data = List.of("type,fruit,quantity",
                "s,apple,20",
                "s,banana,30");
        List<FruitTransactionDto> result = fruitDataParser.parse(data);
        assertEquals(NUMBER_OF_ELEMENTS, result.size());
        assertEquals(firstDto, result.get(FIRST_ELEMENT_INDEX));
        assertEquals(secondDto, result.get(SECOND_ELEMENT_INDEX));
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
