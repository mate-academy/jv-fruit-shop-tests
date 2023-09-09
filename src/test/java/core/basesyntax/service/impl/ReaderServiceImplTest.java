package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static ReaderServiceImpl reader;
    private static List<String> fileLines;

    @BeforeAll
    public static void initReader() {
        reader = new ReaderServiceImpl();
    }

    @BeforeEach
    public void initFileLines() {
        fileLines = new ArrayList<>();
        fileLines.add("type,fruit,quantity");
        fileLines.add("b,banana,20");
        fileLines.add("b,apple,100");
        fileLines.add("s,banana,100");
        fileLines.add("p,banana,13");
        fileLines.add("r,apple,10");
        fileLines.add("p,apple,20");
        fileLines.add("p,banana,5");
        fileLines.add("s,banana,50");
    }

    @Test
    public void implementedCorrectInterface_OK() {
        List<Class<?>> interfaces = Arrays.asList(reader.getClass().getInterfaces());
        Assertions.assertEquals(1, interfaces.size());
        Assertions.assertTrue(interfaces.contains(ReaderService.class));
    }

    @Test
    public void readFromFile_readValidFile_OK() {
        String filePath = "src/main/resources/Test.csv";
        List<String> readLines = reader.readFromFile(filePath);
        Assertions.assertTrue(fileLines.containsAll(readLines)
                && readLines.containsAll(fileLines));
    }

    @Test
    public void readFromFile_wrongFilePath_notOK() {
        String filePath = "src/main/resources/Read.csv";
        Assertions.assertThrows(RuntimeException.class,
                () -> reader.readFromFile(filePath));
    }
}
