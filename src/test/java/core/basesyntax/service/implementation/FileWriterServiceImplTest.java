package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.exeption.FruitShopException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String INCORRECT_PATH = "F://incorrectPath.csv";
    private static final String PATH_TO_FILE_OK = "src/test/resources/Report.csv";
    private static final String HEADER = "fruit,quantity";
    private static final String SECOND_LINE = "banana,50";
    private static List<String> listOk;
    private static FileWriterServiceImpl fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
        listOk = new ArrayList<>();
        listOk.add(HEADER);
        listOk.add(SECOND_LINE);
    }

    @Test(expected = FruitShopException.class)
    public void writeToFile_incorrectPath_notOk() {
        fileWriterService.writeToFile(INCORRECT_PATH, HEADER);
        fail("Expected " + FruitShopException.class.getName() + " but wasn't!");
    }

    @Test
    public void writeToFile_ok() {
        String message = HEADER + System.lineSeparator() + SECOND_LINE;
        fileWriterService.writeToFile(PATH_TO_FILE_OK, message);

        File file = new File(PATH_TO_FILE_OK);
        try (BufferedReader ignored = new BufferedReader(new FileReader(file))) {
            final List<String> readAllLines = Files.readAllLines(Path.of(PATH_TO_FILE_OK));
            assertEquals(readAllLines, listOk);
        } catch (IOException e) {
            throw new FruitShopException("Can't read from file " + file);
        }
    }
}
