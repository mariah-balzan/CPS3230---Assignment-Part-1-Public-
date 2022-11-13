package test.cps3230.stubs;

import org.example.market.MarketService;

public class MarketServiceIsFavourable implements MarketService {

    @Override
    public boolean isMarketDisplayingLatestAlerts() {
        return true;
    }
}
