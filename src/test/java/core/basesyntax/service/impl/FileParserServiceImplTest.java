package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitOperation;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileParserServiceImplTest {
    private static final FileParserServiceImpl fileParser = new FileParserServiceImpl();

    @Test
    void parseFile_NullValues_NotOk() {
        assertThrows(RuntimeException.class, () -> fileParser.parseFile(null));
    }

    @Test
    void parseFile_InCorrectContent_notOk() {
        String[] inCorrectQuantity = new String[] { "b,banana,-100", "b,apple,-200"};
        String[] inCorrectOperation = new String[] { "a,banana,100", "a,apple,200"};
        assertThrows(RuntimeException.class, () -> fileParser.parseFile(inCorrectQuantity));
        assertThrows(RuntimeException.class, () -> fileParser.parseFile(inCorrectOperation));
    }

    @Test
    void parseFile_CorrectContent_Ok() {
        String[] correctContent = new String[] { "b,banana,100", "b,apple,200"};
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitOperation.BALANCE,
                "banana", 100));
        expected.add(new FruitTransaction(FruitOperation.BALANCE,
                "apple", 200));
        List<FruitTransaction> actual = fileParser.parseFile(correctContent);
        assertEquals(expected, actual);
    }
}
