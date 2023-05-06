package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.ReadService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    private final ReadService readService = new ReadServiceImpl();
    private final FruitTransactionService fruitService = new FruitTransactionServiceImpl();
    private Path tempFile;
    
    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("test", ".csv");
    }
    
    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }
    
    @Test
    void create_newTransaction_Ok() throws IOException {
        List<String> list = List.of("type,fruit,quantity",
                "b,banana,100",
                "s,banana,20",
                "b,apple,20",
                "s,apple,100",
                "p,banana,50",
                "p,apple,40",
                "r,banana,10",
                "r,apple,20");
        
        Files.write(tempFile, list);
        fruitService.createNewFruitTransaction(readService.readFile(tempFile));
        List<FruitTransaction> expected = new ArrayList<>();
        
        for (int i = 1; i < list.size(); i++) {
            FruitTransaction fruitTransaction = new FruitTransaction();
            String[] dataValue = list.get(i).split(",");
            for (Operation operation : Operation.values()) {
                if (operation.getCode().equals(dataValue[0])) {
                    fruitTransaction.setOperation(operation);
                    break;
                }
            }
            fruitTransaction.setFruit(dataValue[1]);
            fruitTransaction.setQuantity(Integer.parseInt(dataValue[2]));
            expected.add(fruitTransaction);
        }
        assertEquals(expected, Storage.fruitShopData);
    }
    
    @Test
    void add_NotValidOperation_NotOk() throws IOException {
        Files.write(tempFile, List.of("type,fruit,quantity", "l,banana,100", "t,banana,20"));
        assertThrows(ValidationException.class, () -> {
            fruitService.createNewFruitTransaction(readService.readFile(tempFile));
        });
    }
    
    @Test
    void add_NotValidFruitName_NotOk() throws IOException {
        Files.write(tempFile, List.of("type,fruit,quantity", "b,1ana,100"));
        assertThrows(ValidationException.class, () -> {
            fruitService.createNewFruitTransaction(readService.readFile(tempFile));
        });
    }
    
    @Test
    void add_QuantityString_NotOk() throws IOException {
        Files.write(tempFile, List.of("type,fruit,quantity", "b,banana,number"));
        assertThrows(NumberFormatException.class, () -> {
            fruitService.createNewFruitTransaction(readService.readFile(tempFile));
        });
    }
    
    @Test
    void add_NegativeQuantity_NotOk() throws IOException {
        Files.write(tempFile, List.of("type,fruit,quantity", "b,banana,-100"));
        assertThrows(ValidationException.class, () -> {
            fruitService.createNewFruitTransaction(readService.readFile(tempFile));
        });
    }
}
