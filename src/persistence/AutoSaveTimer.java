package persistence;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

// This class use a timer to periodically call DataManager.save(state);

public class AutoSaveTimer {

    private Timer timer;
    private AppState state;
    private boolean running;

    public AutoSaveTimer(AppState state, int intervalMinutes) {
        this.state = state;
        running = true;
        this.timer = new Timer("AutoSave", true);
        long ms = intervalMinutes * 60 * 1000L;

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                try {
                    DataManager.save(state);
                    System.out.println("[AutoSave] Periodic save done.");
                } catch (IOException e) {
                    System.out.println("[AutoSave] Save failed: " + e.getMessage());
                }
            }
        }, ms, ms);

        System.out.println("[AutoSave] Started — every " + intervalMinutes + " min.");
    }

    public void stop() {
        timer.cancel();
        running = false;
    }

    public void setState(AppState s) { this.state = s; }
    public boolean isRunning() { return running; }
}
