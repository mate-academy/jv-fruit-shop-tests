package service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;
import model.OperationType;
import model.dto.FruitRecordDto;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ParserService;

public class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeClass
    public static void setFruitRecordDtoParser() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parseToDto_validData_Ok() {
        List<String> dataFromFile = List.of("b,banana,20", "b,apple,30");
        List<FruitRecordDto> expected = List.of(
                new FruitRecordDto(OperationType.BALANCE, "banana", 20),
                new FruitRecordDto(OperationType.BALANCE, "apple", 30));
        List<FruitRecordDto> actual = parserService.parseToDto(dataFromFile);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseToDto_invalidOperationType_RuntimeException() {
        List<String> dataFromFile = List.of("c,banana,20", "b,apple,30");
        parserService.parseToDto(dataFromFile);
    }

    @Test(expected = RuntimeException.class)
    public void parseToDto_invalidFruitName_RuntimeException() {
        List<String> dataFromFile = List.of("b,ban11ana,20", "b,apple,30");
        parserService.parseToDto(dataFromFile);
    }

    @Test(expected = RuntimeException.class)
    public void parseToDto_negativeQuantity_RuntimeException() {
        List<String> dataFromFile = List.of("b,banana,20", "b,apple,-30");
        parserService.parseToDto(dataFromFile);
    }

    @Test(expected = RuntimeException.class)
    public void parseToDto_quantityContainsNonDigits_RuntimeException() {
        List<String> dataFromFile = List.of("b,banana,2a0", "b,apple,30");
        parserService.parseToDto(dataFromFile);
    }
}
