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
    public static final String FILE_PATH = "src/test/resources/input.csv";
    public static final String WRONG_FILE_PATH = "src/test/resources/test/input.csv";
    public static final FruitTransactionDto firstDto =
            new FruitTransactionDto("s", "apple", 20);
    public static final FruitTransactionDto secondDto =
            new FruitTransactionDto("s", "banana", 30);
    private FruitDataReaderServiceImpl fruitDataReaderService =
            new FruitDataReaderServiceImpl(new FruitDataParser());

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testReadData_Ok() {
        List<FruitTransactionDto> expected = List.of(firstDto, secondDto);
        List<FruitTransactionDto> actual = fruitDataReaderService.readData(FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void testReadData_FileNotFound_NotOk() {
        assertThrows(DataReaderExeption.class, () -> fruitDataReaderService.readData(WRONG_FILE_PATH));
    }
}
