package komodocrypto.services.data_collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ScheduledTasks {

    @Autowired
    CryptoCompareHistoricalService cryptoCompareHistoricalService;

    private boolean cronHit = false;
    private ArrayList<Integer> timestampsMinutely;

    // Generates a timestamp at 0:01 for previous day's data.
    @Scheduled(cron = "0 */5 * * * *", zone = "GMT")
    private void queryTimestampMinutely() {

        cronHit = false;
        int now = (int) (System.currentTimeMillis() / 1000);
        int secInMin = CryptoCompareHistoricalService.SEC_IN_MIN;
        int limit = now - secInMin;
        boolean found = false;

        for (int i = now; i > limit || found == true; i--) {

            if (i % secInMin == 0) {

                for (int j = 0; j < 5; j++)
                timestampsMinutely.add(i - secInMin * j);
                cronHit = true;
                found = true;
            }
        }

        // Calls the switcher method which cycles through pair/exchange combos and directs the proper method to query
        // for timestampDaily.
        cryptoCompareHistoricalService.switchDataOperations();
    }

    // Determines whether the scheduled task at midnight has run.
    public boolean isCronHit() {

        if (cronHit == true) {
            return true;
        } else {
            return false;
        }
    }

    // Getter method for timestamp generated for queryDaily scheduled task.
    public ArrayList<Integer> getTimestampsMinutely() {
        return timestampsMinutely;
    }
}
