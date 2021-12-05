package database;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static FileService fileService;
    private static final String CORRECT_PATH = "src/main/java/database/inputValue.csv";
    private static final String INCORRECT_PATH = "src/main/java/database/inputValue";
    private static final String EMPTY_FILE = "src/main/java/database/outPutValue.csv";

    @BeforeClass
    public static void start() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void readingFileFromCorrectPath_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50",
                "p,banana,0",
                "b,plum,19",
                "r,plum,10",
                "s,lemon,11",
                "b,lemon,1",
                "b,pineapple,32",
                "r,pineapple,2",
                "s,pineapple,1",
                "p,pineapple,3");
        List<String> actual = fileService.readFile(CORRECT_PATH);
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void readingFileFromIncorrectPath_Not_Ok() {
        List<String> actual = fileService.readFile(INCORRECT_PATH);
    }

    @Test
    public void emptyWriteData_Ok() {
        String emptyLine = new String();
        fileService.writeToFile(EMPTY_FILE, emptyLine);
    }

    @Test(expected = RuntimeException.class)
    public void emptyFileNameWrite_NotOk() {
        fileService.writeToFile("", "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "r,apple,10\n"
                + "p,apple,20\n"
                + "p,banana,5\n"
                + "s,banana,50\n"
                + "p,banana,0\n"
                + "b,plum,19\n"
                + "r,plum,10\n"
                + "s,lemon,11\n"
                + "b,lemon,1\n"
                + "b,pineapple,32\n"
                + "r,pineapple,2\n"
                + "s,pineapple,1\n"
                + "p,pineapple,3");
    }

    @Test
    public void fileWrite_Ok() {
        String validInput = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "r,apple,10\n"
                + "p,apple,20\n"
                + "p,banana,5\n"
                + "s,banana,50\n"
                + "p,banana,0\n"
                + "b,plum,19\n"
                + "r,plum,10\n"
                + "s,lemon,11\n"
                + "b,lemon,1\n"
                + "b,pineapple,32\n"
                + "r,pineapple,2\n"
                + "s,pineapple,1\n"
                + "p,pineapple,3";
        fileService.writeToFile("src/main/java/database/inputValue.csv", validInput);
    }
}
