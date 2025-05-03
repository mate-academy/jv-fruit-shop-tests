package core.basesyntax;

import static org.junit.Assert.assertArrayEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FileParserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileParserServiceTest {
    private static FileParserService fileParser;

    @AfterEach
    void clearStorage() {
        Storage.Storage.clear();
    }

    @BeforeAll
    static void setUp() {
        fileParser = new FileParserService();
    }

    @Test
    void parse_validInput_ok() {
        String data = "type,fruit,quantityb,banana,20b,apple,100s,"
                + "banana,100p,banana,13r,apple,10p,apple,20p,banana,5s,banana,50";
        String[] expectedResult = {"b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50"};
        assertArrayEquals(expectedResult, fileParser.parse(data));
    }
}
