package pl.piomin.quarkus.ai.api.external;

import java.util.List;

public class StockData {

    private List<DailyStockData> values;
    
    private Meta meta;

    public List<DailyStockData> getValues() {
        return values;
    }

    public void setValues(List<DailyStockData> values) {
        this.values = values;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
    
}
