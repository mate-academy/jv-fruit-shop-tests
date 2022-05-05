package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_ok() {
        String toFileName = "testOutputFile";
        String report = "fruit,quantity\n"
                + "banana,52\n"
                + "apple,9\n";
        List<String> expected = List.of("fruit,quantity",
                "banana,52",
                "apple,9");
        fileWriterService.writeToFile(toFileName, report);
        File testOutputFile = new File("testOutputFile");
        List<String> actual = new ArrayList<>();
        actual = read("testOutputFile");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_notOk() {
        String report = "fruit,quantity \n"
                + "banana,52\n"
                + "apple,9\n";
        fileWriterService.writeToFile("", report);
    }

    private static List<String> read(String filePath) {
        List<String> readData = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            readData = lines.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Cant`t read data from file"
                    + Path.of(filePath).getFileName().toString(), e);
        }
        return readData;
    }
}
