package komodocrypto.model.cryptocompare.news;

import com.fasterxml.jackson.annotation.JsonProperty;

public class News {

    @JsonProperty("Data")
    Data[] data;

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }
}
