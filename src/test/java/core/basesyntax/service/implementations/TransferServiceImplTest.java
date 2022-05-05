package core.basesyntax.service.implementations;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransferDto;
import core.basesyntax.service.inerfaces.TransferService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransferServiceImplTest {
    private static TransferService transferService;

    @BeforeClass
    public static void beforeAll() {
        transferService = new TransferServiceImpl();
    }

    @Test
    public void validate_ValidInput_Ok() {
        List<String> list = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"
        );
        List<FruitTransferDto> actual = transferService.parse(list);
        List<FruitTransferDto> expected = List.of(
                new FruitTransferDto("b", new Fruit("banana"), 20),
                new FruitTransferDto("b", new Fruit("apple"), 100),
                new FruitTransferDto("s", new Fruit("banana"), 100)
        );
        Assert.assertEquals(expected, actual);
    }
}
