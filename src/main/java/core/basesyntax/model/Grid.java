package core.basesyntax.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Grid {
    private String[] titles;
    private List<String[]> rows;

    public Grid(String[] titles, List<String[]> rows) {
        this.titles = titles;
        this.rows = rows;
    }

    public String[] getTitles() {
        return titles;
    }

    public List<String[]> getRows() {
        return rows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Grid grid = (Grid) o;
        if (rows.size() != grid.rows.size()) {
            return false;
        }
        for (int i = 0; i < rows.size(); i++) {
            if (!Arrays.equals(rows.get(i), grid.rows.get(i))) {
                return false;
            }
        }
        return Arrays.equals(titles, grid.titles);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows);
        result = 31 * result + Arrays.hashCode(titles);
        return result;
    }
}
