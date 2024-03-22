package core.basesyntax.service.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.exeptions.DataReaderExeption;
import core.basesyntax.service.FruitDataParser;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitDataReaderServiceImplTest {
    private FruitDataReaderServiceImpl fruitDataReaderService;

    @BeforeEach
    public void setUp() {
        fruitDataReaderService = new FruitDataReaderServiceImpl(new FruitDataParser());
    }

    @Test
    public void testReadData_Ok() {
        String filePath = "src/test/resources/input.csv";
        FruitTransactionDto firstDto = new FruitTransactionDto("s", "apple", 20);
        FruitTransactionDto secondDto = new FruitTransactionDto("s", "banana", 30);
        List<FruitTransactionDto> expected = List.of(firstDto, secondDto);
        List<FruitTransactionDto> actual = fruitDataReaderService.readData(filePath);
        assertEquals(expected, actual);
    }

    @Test
    public void testReadData_FileNotFound_NotOk() {
        String filePath = "src/test/resources/test/input.csv";
        assertThrows(DataReaderExeption.class, () -> fruitDataReaderService.readData(filePath));
    }
}
