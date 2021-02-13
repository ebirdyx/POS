package store;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileStore implements Store {
    private String filename;

    public FileStore(String filename) {
        this.filename = filename;
    }

    public File getDatabaseFile() {
        Path root = Paths.get(".").normalize().toAbsolutePath();
        File file = new File(root.toString() + File.separator + filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    @Override
    public void saveData(String[] lines) {
        try {

            FileWriter writer = new FileWriter(getDatabaseFile());

            for (int i = 0; i < lines.length; i++) {
                writer.write(lines[i] + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] loadData() {
        List<String> lines = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(getDatabaseFile()));

            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (lines.size() == 0) {
            return new String[0];
        }

        return lines.toArray(new String[lines.size()]);
    }
}
