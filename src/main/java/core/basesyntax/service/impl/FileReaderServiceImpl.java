package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {
    @Override
    public List<String> readFromFile(String inputFilePath) {
        File inputFile = new File(inputFilePath);
        try {
            List<String> lines = Files.readAllLines(inputFile.toPath());
            if (lines.isEmpty()) {
                throw new RuntimeException("This file is empty. "
                        + inputFilePath + " Please, use valid file");
            }
            return lines;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("This file was not found " + e
                    + "Check the path or the name of File " + inputFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file " + e + " "
                    + inputFilePath + ". Please, use valid file");
        }
    }
}
