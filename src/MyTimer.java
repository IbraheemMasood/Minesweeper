import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A JLabel timer
 */
public class MyTimer extends JLabel {
    private Timer timer;
    private long startTime;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss"); //The displayed format

    /**
     * Creates a visual timer, updating every 1s
     */
    public MyTimer() {
        this.startTime = System.currentTimeMillis();

        setText("0:00");
        setHorizontalAlignment(CENTER);
        setFont(new Font("Inter", Font.PLAIN, 20));


    }

    /**
     * Start the timer, update every second
     */
    void start() {
        this.timer = new Timer();
        TimerTask TimerTask = new TimerTask() {
            @Override
            public void run() {
                setText(getTime());
            }
        };
        timer.schedule(TimerTask, 100, 100);

    }

    /**
     * @return elapsed time
     */
    public String getTime() {

        long elapsedMillis = (System.currentTimeMillis() - startTime);
        return timeFormat.format(elapsedMillis);
    }

    /**
     * Reset the timer to current time
     */
    public void resetTimer(){
        startTime = System.currentTimeMillis();
    }

    /**
     * Stop the timer
     */
    public void stop() {
        timer.cancel();
        resetTimer();
    }

}
