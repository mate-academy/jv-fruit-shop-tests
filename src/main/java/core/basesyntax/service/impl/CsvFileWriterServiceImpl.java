package core.basesyntax.service.impl;

import core.basesyntax.exceptions.PathDoesNotExistException;
import core.basesyntax.exceptions.WrongExtensionException;
import core.basesyntax.service.CsvFileWriterService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvFileWriterServiceImpl implements CsvFileWriterService {
    private static final String CSV_FILE_EXTENSION = "csv";
    private static final String DOT_REGEX = "\\.";

    @Override
    public void writeToFile(String report, String path) throws IOException {
        Path filePath = FileSystems.getDefault().getPath(path);
        if (!Files.exists(filePath)) {
            throw new PathDoesNotExistException("File path doesn't exist!");
        }
        String[] splitPath = path.split(DOT_REGEX);
        String fileExtension = splitPath[splitPath.length - 1];
        if (!fileExtension.equalsIgnoreCase(CSV_FILE_EXTENSION)) {
            throw new WrongExtensionException("You can only use csv files!");
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
        bufferedWriter.write(report);
        bufferedWriter.close();
    }
}
