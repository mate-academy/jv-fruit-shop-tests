package core.basesyntax.services;

import static core.basesyntax.services.OperationType.BALANCE;
import static core.basesyntax.services.OperationType.PURCHASE;
import static core.basesyntax.services.OperationType.RETURN;
import static core.basesyntax.services.OperationType.SUPPLY;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitRecordDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class MapperTest {
    private static Mapper mapper = new Mapper();

    @Test
    public void mapper_apply_isOk() {
        List<String> stringList = new ArrayList<>();
        stringList.add("type,fruit,quantity");
        stringList.add("b,banana,100");
        stringList.add("b,apple,100");
        stringList.add("s,banana,10");
        stringList.add("s,apple,20");
        stringList.add("r,apple,7");
        stringList.add("p,banana,100");
        stringList.add("s,banana,100");
        List<FruitRecordDto> expected = new ArrayList<>();
        expected.add(new FruitRecordDto(BALANCE, "banana", 100));
        expected.add(new FruitRecordDto(BALANCE, "apple", 100));
        expected.add(new FruitRecordDto(SUPPLY, "banana", 10));
        expected.add(new FruitRecordDto(SUPPLY, "apple", 20));
        expected.add(new FruitRecordDto(RETURN, "apple", 7));
        expected.add(new FruitRecordDto(PURCHASE, "banana", 100));
        expected.add(new FruitRecordDto(SUPPLY, "banana", 100));
        List<FruitRecordDto> actual = mapper.apply(stringList);
        assertEquals(expected, actual);
    }

    @Test
    public void mapper_apply_isNotOk() {
        RuntimeException e = null;
        List<String> stringList = new ArrayList<>();
        stringList.add("type,fruit,quantity");
        stringList.add("b,banana,-100");
        Assertions.assertThrows(RuntimeException.class, () ->
                    mapper.apply(stringList));
    }
}
