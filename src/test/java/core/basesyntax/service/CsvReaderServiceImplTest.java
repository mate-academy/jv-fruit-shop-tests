package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.AbstractTransaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.impl.CsvReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvReaderServiceImplTest {

    private static CsvReaderService<Fruit> readerService;
    private static String filePath;
    private static int SIZE_OF_DATA;

    @BeforeAll
    static void setUp() {
        readerService = new CsvReaderServiceImpl();
        try {
            filePath = Files.createTempFile("tempFile", ".csv").toString();
            List<String> lines = List.of("type,fruit,quantity", "b,banana,20", "s,apple,100");
            Files.write(Path.of(filePath), lines);
            SIZE_OF_DATA = lines.size() - 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void parse_operationParsedCorrect_OK() {
        List<AbstractTransaction<Fruit>> result = readerService.parse(filePath);
        AbstractTransaction<Fruit> firstTransaction = result.get(0);
        Assertions.assertEquals(OperationType.BALANCE, firstTransaction.getOperationType());
        Assertions.assertEquals(20, firstTransaction.getQuantity());
        AbstractTransaction<Fruit> secondTransaction = result.get(1);
        Assertions.assertEquals(OperationType.SUPPLY, secondTransaction.getOperationType());
        Assertions.assertEquals(100, secondTransaction.getQuantity());
    }

    @Test
    void parse_resultIsNotNullForValidPath_OK() {
        List<AbstractTransaction<Fruit>> result = readerService.parse(filePath);
        Assertions.assertNotNull(result);
    }

    @Test
    void parse_sizeOfData_OK() {
        List<AbstractTransaction<Fruit>> result = readerService.parse(filePath);
        assertEquals(SIZE_OF_DATA, result.size());
    }

    @Test
    void parse_ShouldThrowRuntimeException_WhenFileNotFound() {
        Assertions.assertThrows(RuntimeException.class, () -> readerService.parse("invalid path"));
    }
}
