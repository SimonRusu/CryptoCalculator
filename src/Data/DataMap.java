package Data;

import java.util.LinkedHashMap;
import java.util.Map;

public final class DataMap {

    
    private final Map<String, String> fiatMap = new LinkedHashMap<String, String>()
    {
        {
            put("usd", "Dólar estadounidense (USD)");
            put("eur", "Euro (EUR)");
            put("gbp", "Libra esterlina (GBP)");
            put("chf", "Franco suizo (CHF)");
            put("aud", "Dólar australiano (AUD)");
            put("jpy", "Yen japonés (JPY)");
            put("cad", "Dólar canadiense (CAD)");
            put("cny", "Renminbi chino (CNY)");
            put("hkd", "Dólar hongkonés (HKD)");
            put("nzd", "Dólar neozelandés (NZD)");
        }
    };
    
    private final Map<String, String> cryptoMap = new LinkedHashMap<String, String>()
    {
        {
            put("bitcoin", "Bitcoin (BTC)");
            put("ethereum", "Ethereum (ETH)");
            put("dogecoin", "Dogecoin (DOGE)");
            put("binancecoin", "Binance Coin (BNB)");
            put("binance-usd", "Binance USD (BUSD)");
            put("radio-caca", "Radio Caca (RACA)");
            put("axie-infinity", "Axie Infinity (AXS)");
            put("shiba-inu", "Shiba Inu (SHIB)");
            put("constellation-labs", "Constellation (DAG)");
            put("step-hero", "Step Hero (HERO)");
            
            
            
        }
    };

    public Map<String, String> getFiatMap() {
        return fiatMap;
    }

    public Map<String, String> getCryptoMap() {
        return cryptoMap;
    }
    
}
