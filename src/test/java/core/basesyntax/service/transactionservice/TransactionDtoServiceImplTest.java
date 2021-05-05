package core.basesyntax.service.transactionservice;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.dto.TransactionDto;
import core.basesyntax.model.operations.Operation;
import core.basesyntax.model.product.Fruit;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionDtoServiceImplTest {
    private static TransactionDtoService transactionDtoService;

    @BeforeClass
    public static void beforeClass() {
        transactionDtoService = new TransactionDtoServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void transform_nullData_notOk() {
        transactionDtoService.transform(null);
    }

    @Test
    public void transform_emptyData_isOk() {
        List<TransactionDto> expected = new ArrayList<>();
        List<String> emptyData = new ArrayList<>();
        List<TransactionDto> actual = transactionDtoService.transform(emptyData);
        assertEquals(expected, actual);
    }

    @Test
    public void transform_isOk() {
        List<String> data = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100");
        List<TransactionDto> expected = List.of(
                new TransactionDto(Operation.B, new Fruit("banana"), 20),
                new TransactionDto(Operation.B, new Fruit("apple"), 100),
                new TransactionDto(Operation.S, new Fruit("banana"), 100));
        List<TransactionDto> actual = transactionDtoService.transform(data);
        assertEquals(expected,actual);
    }

    @Test
    public void transform_dataWithInvalidLine_isOk() {
        List<String> data = List.of("k,banana,20",
                "b,567,100",
                "s,banana,ababagalamaga");
        List<TransactionDto> expected = new ArrayList<>();
        List<TransactionDto> actual = transactionDtoService.transform(data);
        assertEquals(expected,actual);
    }
}
