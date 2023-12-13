package core.basesyntax.service.impl;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.exceptions.PathDoesNotExistException;
import core.basesyntax.exceptions.WrongExtensionException;
import core.basesyntax.service.CsvFileReaderService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReaderServiceImpl implements CsvFileReaderService {
    private static final int COLUMN_NAMES_INDEX = 0;
    private static final int REQUIRED_NUMBER_OF_COLUMNS = 3;
    private static final String CSV_FILE_EXTENSION = "csv";
    private static final String DOT_REGEX = "\\.";
    private static final String COMA_REGEX = ",";

    @Override
    public List<String[]> readFromFile(String path) throws PathDoesNotExistException {
        Path filePath = FileSystems.getDefault().getPath(path);
        if (!Files.exists(filePath)) {
            throw new PathDoesNotExistException("File path doesn't exist!");
        }
        String[] splitPath = path.split(DOT_REGEX);
        String fileExtension = splitPath[splitPath.length - 1];
        if (!fileExtension.equalsIgnoreCase(CSV_FILE_EXTENSION)) {
            throw new WrongExtensionException("You can only use csv files!");
        }
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(COMA_REGEX);
                data.add(fields);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from the file " + path, e);
        }
        if (data.get(COLUMN_NAMES_INDEX).length != REQUIRED_NUMBER_OF_COLUMNS) {
            throw new InvalidDataException("Csv file should have 3 columns!");
        }
        data.remove(COLUMN_NAMES_INDEX);
        return data;
    }
}
