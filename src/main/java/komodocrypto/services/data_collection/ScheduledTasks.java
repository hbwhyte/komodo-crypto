package komodocrypto.services.data_collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ScheduledTasks {

    @Autowired
    CryptoCompareHistoricalService historicalService;

    private boolean weeklyCronHit = false;
    private boolean dailyCronHit = false;
    private boolean hourlyCronHit = false;
    private boolean minutelyCronHit = false;

    private boolean cronHit = false;

    protected ArrayList<Integer> timestampDaily = new ArrayList<>();
    protected ArrayList<Integer> timestampHourly = new ArrayList<>();
    protected ArrayList<Integer> timestampMinutely = new ArrayList<>();

    private int weeklyTimestamp;

    // Generates a timestamp at 0:01 for previous day's data.
    @Scheduled(cron = "0 1 0 * * *", zone = "GMT")
    private void queryTimestampDaily() {

        int now = (int) (System.currentTimeMillis() / 1000);
        int secInDay = CryptoCompareHistoricalService.SEC_IN_MIN * CryptoCompareHistoricalService.MIN_IN_HOUR *
                CryptoCompareHistoricalService.HOURS_IN_DAY;
//        int limit = now - secInDay;
        boolean found = false;

        for (int i = now; found == false; i--) {

            if (i % secInDay == 0) {
                timestampDaily.add(i);
                dailyCronHit = true;
                found = true;
            }
        }

        // Calls the switcher method which cycles through pair/exchange combos and directs the proper method to query
        // for timestampDaily.
        historicalService.switchDataOperations();
    }

    // Generates a timestamp in the first minute of every hour for the previous hour's data.
    @Scheduled(cron = "0 1 * * * *", zone = "GMT")
    private void queryTimestampHourly() {

        int now = (int) (System.currentTimeMillis() / 1000);
        int secInHour = CryptoCompareHistoricalService.SEC_IN_MIN * CryptoCompareHistoricalService.MIN_IN_HOUR;
//        int limit = now - secInHour;
        boolean found = false;

        for (int i = now; found == false; i--) {

            if (i % secInHour == 0) {
                timestampHourly.add(i);
                hourlyCronHit = true;
                found = true;
            }
        }

        historicalService.switchDataOperations();
    }

    // Generates an array list of timestamps every five minutes for the previous five minutes.
    @Scheduled(cron = "0 */5 * * * *", zone = "GMT")
    private void queryTimestampMinutely() {

        int now = (int) (System.currentTimeMillis() / 1000);
        int secInMin = CryptoCompareHistoricalService.SEC_IN_MIN;
//        int limit = now - secInMin;
        boolean found = false;

        for (int j = 0; j < 5; j++) {
            timestampMinutely.add(now - secInMin * j);
            found = true;
        }

        minutelyCronHit = true;
        historicalService.switchDataOperations();
    }

    @Scheduled(cron = "0 3 0 */7 * *", zone = "GMT")
    private void queryTimestampWeekly() {

        int now = (int) (System.currentTimeMillis() / 1000);
        int secInHour = CryptoCompareHistoricalService.SEC_IN_MIN * CryptoCompareHistoricalService.MIN_IN_HOUR;
        int secInWeek = secInHour * CryptoCompareHistoricalService.HOURS_IN_DAY * 7;
//        int limit = now - secInWeek;
        boolean found = false;        int weeklyTs = 0;

        for (int i = now; found == false; i--) {

            if (i % secInHour == 0) {
                weeklyTs = i;
                weeklyCronHit = true;
                found = true;
            }
        }

        weeklyTimestamp = weeklyTs;
    }

    public boolean isWeeklyCronHit() {
        return weeklyCronHit;
    }

    // Determines whether the scheduled task at midnight has run.
    public boolean isDailyCronHit() {

        if (dailyCronHit == true) return true;
        else return false;
    }

    // Determines whether the hourly scheduled task has run.
    public boolean isHourlyCronHit() {

        if (hourlyCronHit == true) return true;
        else return false;
    }

    // Determines whether the minutely scheduled task has run.
    public boolean isMinutelyCronHit() {

        if (minutelyCronHit == true) return true;
        else return false;
    }

    public void setWeeklyCronHit(boolean weeklyCronHit) {
        this.weeklyCronHit = weeklyCronHit;
    }

    public void setDailyCronHit(boolean dailyCronHit) {
        this.dailyCronHit = dailyCronHit;
    }

    public void setHourlyCronHit(boolean hourlyCronHit) {
        this.hourlyCronHit = hourlyCronHit;
    }

    public void setMinutelyCronHit(boolean minutelyCronHit) {
        this.minutelyCronHit = minutelyCronHit;
    }


    public void setWeeklyTimestamp(int weeklyTimestamp) {
        this.weeklyTimestamp = weeklyTimestamp;
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

    public int getWeeklyTimestamp() {
        return weeklyTimestamp;
    }


    public boolean isCronHit() {
        return cronHit;
    }

    public void setCronHit(boolean cronHit) {
        this.cronHit = cronHit;
    }
}