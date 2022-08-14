package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileReaderServiceImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String BANANA_KEY = "banana";
    private static final String APPLE_KEY = "apple";
    private static FileReaderService fileReaderService;
    private static String correctNameFile;
    private static String notCorrectNameFile;
    private static ArrayList<String> expected;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        correctNameFile = "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "ProductBalance.csv";
        notCorrectNameFile = "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "Product.csv";
        fileReaderService = new FileReaderServiceImpl();
        expected = new ArrayList<>(List.of(HEADER + " b, "
                + APPLE_KEY + ", 100, "
                + " b, " + BANANA_KEY + ", 100"));
        String data = HEADER + " b, "
                + APPLE_KEY + ", 100, "
                + " b, " + BANANA_KEY + ", 100";

        File filePath = new File(correctNameFile);
        try {
            Files.writeString(filePath.toPath(), data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write file " + correctNameFile);
        }
    }

    @Test
    public void readFromFile_notOk() throws RuntimeException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Can't read file src/main/resources/Product.csv");
        fileReaderService.readFromFile(notCorrectNameFile);
    }

    //Не уверен что он нужен так как если будет
    @Test(expected = RuntimeException.class)
    public void readFromFileNull_notOk() {
        fileReaderService.readFromFile(null);
    }

    @Test
    public void readFromFile_Ok() {
        List<String> actual = fileReaderService.readFromFile(correctNameFile);
        assertEquals("Test failed! Waiting information "
                + expected + " but was: " + actual, expected, actual);
    }

    @AfterClass
    public static void cleanResources() {
        expected.clear();
    }
}
