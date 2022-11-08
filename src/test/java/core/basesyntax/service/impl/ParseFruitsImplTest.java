package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseFruits;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class ParseFruitsImplTest {
    private static final String FILE_PATH = "src/test/resources/fruitInfo.csv";
    private ParseFruits parseFruits = new ParseFruitsImpl();

    @Test
    public void parseIncorrectData_NotOk() {
        assertThrows(RuntimeException.class,
                () -> parseFruits.transactions(null), "Incorrect data");
    }

    @Test
    public void parseCorrectData_Ok() {
        List<String> list = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,18",
                "p,banana,5",
                "s,banana,500");
        List<FruitTransaction> actual = parseFruits.transactions(list);
        List<FruitTransaction> expected = null;
        try {
            List<String> collect = Files.readAllLines(Paths.get(FILE_PATH))
                    .stream().collect(Collectors.toList());
            expected = parseFruits.transactions(collect);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected.toString(), actual.toString());
    }
}
