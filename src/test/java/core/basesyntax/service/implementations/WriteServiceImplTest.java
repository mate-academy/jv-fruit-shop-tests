package core.basesyntax.service.implementations;

import core.basesyntax.service.inerfaces.WriteService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteServiceImplTest {
    private static final String TO_FILE_ROUTE
            = "src/test/java/core/basesyntax/resources/ToFile.cvs";
    private static String input;
    private static WriteService write;

    @BeforeClass
    public static void beforeAll() {
        input = List.of(
                "banana,120",
                "apple,70"
        ).stream().collect(Collectors.joining(System.lineSeparator()));
    }

    @Before
    public void beforeEach() {
        write = new WriteServiceImpl();
    }

    @Test
    public void writeToFile_CorrectData_Ok() {
        write.writeToFile(TO_FILE_ROUTE, input);
        List<String> expected = List.of(
                "fruit,quantity",
                "banana,120",
                "apple,70"
        );
        List<String> actual = readFile(TO_FILE_ROUTE);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullRoute_NotOk() {
        write.writeToFile(null, input);
    }

    @Test
    public void writeToFile_nullData_NotOk() {
        write.writeToFile(TO_FILE_ROUTE, null);
        List<String> expected = List.of(
                "fruit,quantity",
                "null"
        );
        List<String> actual = readFile(TO_FILE_ROUTE);
        Assert.assertEquals(expected, actual);
    }

    private List<String> readFile(String filePath) {
        try (BufferedReader read = new BufferedReader(new FileReader(filePath))) {
            List<String> list = read.lines().collect(Collectors.toList());
            return list;
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + filePath, e);
        }
    }
}
