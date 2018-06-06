package komodocrypto.services.data_collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Service
public class ScheduledTasks {

    @Autowired
    CryptoCompareHistoricalService historicalService;

    private boolean cronHit = false;

    protected int timestampWeekly;
    protected ArrayList<Integer> timestampDaily = new ArrayList<>();
    protected ArrayList<Integer> timestampHourly = new ArrayList<>();
    protected ArrayList<Integer> timestampMinutely = new ArrayList<>();

    /**
     * Gets the timestamp of midnight of the current day at 0:02.
     */
    @Scheduled(cron = "0 2 0 * * *", zone = "GMT")
    private void queryTimestampDaily() {

        int now = (int) (System.currentTimeMillis() / 1000);
        int midnight = now - CryptoCompareHistoricalService.SEC_IN_MIN * 2;
        cronHit = true;

        timestampDaily.add(midnight);
        historicalService.switchCronOps("day");

        // Resets the global variables.
        cronHit = false;
        timestampDaily.clear();
    }

    /**
     * Gets the timestamp of the previous hour in the first minute of every hour and adds social media data for the
     * previous hour.
     */
    @Scheduled(cron = "0 1 * * * *", zone = "GMT")
    private void queryTimestampHourly() {

        int now = (int) (System.currentTimeMillis() / 1000);
        int hour = now - CryptoCompareHistoricalService.SEC_IN_MIN;
        cronHit = true;

        timestampHourly.add(hour);
        historicalService.switchCronOps("hour");
        historicalService.addSocial();

        // Resets the global variables.
        cronHit = false;
        timestampHourly.clear();
    }

    /**
     * Generates an array list of timestamps every five minutes for the previous five minutes.
     */
    @Scheduled(cron = "0 */5 * * * *", zone = "GMT")
    private void queryTimestampMinutely() {

        int now = (int) (System.currentTimeMillis() / 1000);
        cronHit = true;

        // Adds social data every hour.
        // The reason this method call is here rather than in the hourly table is because the endpoint does not allow
        // specifying a timestamp, and the hourly task actually runs 60 seconds after the hour has begun. Having it here
        // allows more accurate data collection.
        if (now % (CryptoCompareHistoricalService.SEC_IN_MIN * CryptoCompareHistoricalService.MIN_IN_HOUR) == 0) {
            historicalService.addSocial();
        }

        for (int j = 0; j < 5; j++) {
            timestampMinutely.add(now - CryptoCompareHistoricalService.SEC_IN_MIN * j);
        }

        historicalService.switchCronOps("minute");

        // Resets the global variables.
        cronHit = false;
        timestampMinutely.clear();
    }

    /**
     * Gets the timestamp of midnight of the start of the week at 0:03.
     */
    @Scheduled(cron = "0 3 0 */7 * *", zone = "GMT")
    private void queryTimestampWeekly() {

        int now = (int) (System.currentTimeMillis() / 1000);
        int week = now - CryptoCompareHistoricalService.SEC_IN_MIN * 3;
        cronHit = true;

        timestampWeekly = week;
        historicalService.switchCronOps("week");

        // Resets the global variables.
        cronHit = false;
        timestampMinutely.clear();
    }

    public int getTimestampWeekly() {
        return timestampWeekly;
    }

    public ArrayList<Integer> getTimestampDaily() {
        return timestampDaily;
    }

    public ArrayList<Integer> getTimestampHourly() {
        return timestampHourly;
    }

    public ArrayList<Integer> getTimestampMinutely() {
        return timestampMinutely;
    }

    public boolean isCronHit() {
        return cronHit;
    }

    public void setCronHit(boolean cronHit) {
        this.cronHit = cronHit;
    }
}