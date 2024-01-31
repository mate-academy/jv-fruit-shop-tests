package impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileReaderImplTest {
    private static final String PATH = "src/test/java/TestsFiles/TestInputData.csv";

    @Test
    public void reader_Test_Ok() {
        FileReaderImpl reader = new FileReaderImpl();
        String[] expectedDataArray = new String[] {
                "b,banana,100",
                "b,apple,100",
                "s,apple,10",
                "s,banana,100",
                "p,apple,10",
                "p,apple,20",
                "r,banana,10",
                "r,apple,10"
        };
        List<String> expectedData = List.of(expectedDataArray);
        assertEquals(reader.readFile(PATH), expectedData,
                "Your FileReaderImpl class work incorrectly");
    }
}
