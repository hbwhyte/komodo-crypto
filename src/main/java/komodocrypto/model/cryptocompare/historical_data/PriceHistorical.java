package komodocrypto.model.cryptocompare.historical_data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceHistorical {

    @JsonProperty("Data")
    Data[] data;
    @JsonProperty("TimeTo")
    int timeTo;
    @JsonProperty("TimeFrom")
    int timeFrom;

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    public int getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(int timeTo) {
        this.timeTo = timeTo;
    }

    public int getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(int timeFrom) {
        this.timeFrom = timeFrom;
    }
}
