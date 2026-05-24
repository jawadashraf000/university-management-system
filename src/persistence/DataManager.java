package persistence;

import java.io.*;
import java.nio.file.*;

// This class helps to save and load Object of type AppState in File and out of File.

public class DataManager {

    private static final String DATA_DIR = "data";
    private static final String SAVE_FILE = DATA_DIR + File.separator + "campus_data.dat";
    private static final String BACKUP_FILE = DATA_DIR + File.separator + "campus_data_backup.dat";

    private DataManager() {}

    public static void save(AppState state) throws IOException {
        ensureDirectory();
        backupExisting();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(state);
            System.out.println("[DataManager] Saved.");
        }
    }

    public static AppState load() {
        File f = new File(SAVE_FILE);
        if (!f.exists() || f.length() == 0) {
            return loadBackup();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            AppState state = (AppState) ois.readObject();
            System.out.println("[DataManager] Loaded.");
            return state;
        } catch (EOFException e) {
            System.out.println("[DataManager] File empty/corrupted. Trying backup.");
            return loadBackup();
        } catch (ClassNotFoundException e) {
            System.out.println("[DataManager] Class mismatch. Trying backup.");
            return loadBackup();
        } catch (IOException e) {
            System.out.println("[DataManager] IO error: " + e.getMessage() + ". Trying backup.");
            return loadBackup();
        }
    }

    private static AppState loadBackup() {
        File backup = new File(BACKUP_FILE);
        if (!backup.exists() || backup.length() == 0) {
            System.out.println("[DataManager] No backup found. Starting fresh.");
            return new AppState();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(backup))) {
            AppState state = (AppState) ois.readObject();
            System.out.println("[DataManager] Loaded from backup.");
            return state;
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("[DataManager] Backup unreadable. Starting fresh.");
            return new AppState();
        }
    }

    private static void backupExisting() {
        File primary = new File(SAVE_FILE);
        if (!primary.exists()) return;
        try {
            Files.copy(primary.toPath(), Paths.get(BACKUP_FILE), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("[DataManager] Backup failed: " + e.getMessage());
        }
    }

    private static void ensureDirectory() throws IOException {
        File dir = new File(DATA_DIR);
        if (!dir.exists() && !dir.mkdirs())
            throw new IOException("Cannot create data directory.");
    }

    public static boolean saveFileExists()  { return new File(SAVE_FILE).exists(); }
    public static String  getSaveFilePath() { return SAVE_FILE; }
}
