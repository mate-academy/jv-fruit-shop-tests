package core.basesyntax.service.impl;

import core.basesyntax.exception.GridIoException;
import core.basesyntax.model.Grid;
import core.basesyntax.service.GridReadService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvGridReadService implements GridReadService {
    private static final String SEPARATOR = ",";
    private Grid grid;

    @Override
    public Grid getGrid(String path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String rawTitles = bufferedReader.readLine();
            if (rawTitles == null) {
                return null;
            }
            String[] titles = rawTitles.split(SEPARATOR);
            List<String[]> rows = new ArrayList<>();
            while (bufferedReader.ready()) {
                rows.add(bufferedReader.readLine().split(SEPARATOR));
            }
            return new Grid(titles, rows);
        } catch (FileNotFoundException e) {
            throw new GridIoException("File " + path + " doesn't exist!");
        } catch (IOException e) {
            throw new GridIoException("Failed to read " + path + " file!");
        }
    }
}
