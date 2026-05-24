import gui.LoginFrame;

import persistence.AppState;
import persistence.DataManager;

public class Main {
    public static void main(String[] args) {
        if (!DataManager.saveFileExists()) {
            Test.sampleData();
        }

        AppState state = DataManager.load();
        new LoginFrame(state);
    }
}
