package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.ReadService;
import core.basesyntax.service.WriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class WriteServiceImplTest {
    private final Path invalidPath = Path.of("src/test/java/resources/invalidFile.csv");
    private final Path inputPath = Path.of("src/test/java/resources/inputTestFile.csv");
    private final Path repotPath = Path.of("src/test/java/resources/reportTestFile.csv");
    private final FruitTransactionService fruitTransactionService =
            new FruitTransactionServiceImpl();
    private final ReadService readService = new ReadServiceImpl();
    private final WriteService writeService = new WriteServiceImpl();
    
    @Test
    void write_validData_Ok() throws IOException {
        List<String> expected = List.of("fruit,quantity", "banana,20", "apple,100");
        fruitTransactionService.createNewFruitTransaction(readService.readFile(inputPath));
        writeService.writeToFile(repotPath);
        List<String> actual = Files.readAllLines(repotPath);
        assertEquals(expected, actual);
        Storage.fruitShopData.clear();
    }
    
    @Test
    void write_toInvalidPath_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionService.createNewFruitTransaction(readService.readFile(inputPath));
            writeService.writeToFile(invalidPath);
        });
        Storage.fruitShopData.clear();
    }
}
