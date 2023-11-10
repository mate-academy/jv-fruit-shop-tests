package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReader implements FileReaderService {
    private static final String RUNTIME_EXCEPTION_MESSAGE = "Can't find file by path: ";
    private static final String FILE_IS_EMPTY_MESSAGE = "Empty file by path: ";

    @Override
    public List<String> read(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            if (Files.size(Paths.get(filePath)) == 0) {
                throw new RuntimeException(FILE_IS_EMPTY_MESSAGE + filePath);
            }
            List<String> csvData = new ArrayList<>();
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                csvData.add(line);
            }
            return csvData;
        } catch (IOException e) {
            throw new RuntimeException(RUNTIME_EXCEPTION_MESSAGE + filePath, e);
        }
    }
}
