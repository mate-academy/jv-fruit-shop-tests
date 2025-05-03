package fruitshop.service;

import fruitshop.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReaderServiceTest {
    private static final String READ_TEST_FILE_NAME = "testData.csv";
    private ReaderServiceImpl reader;

    @BeforeAll
    void setUp() {
        reader = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_ReturnsExpectedString_Ok() {
        String[] expectedArray = {
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,150",
                "p,banana,13",
                "r,apple,10"};
        List<String> expected = List.of(expectedArray);

        Assertions.assertEquals(reader.readFromFile(READ_TEST_FILE_NAME),
                expected,
                "ReaderServiceImpl doesn't work properly");
    }
}
