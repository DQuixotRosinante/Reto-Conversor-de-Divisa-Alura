import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ConversorDeDivisa {
    private static final String API_URL = "https://openexchangerates.org/api/latest.json";
    private static final String APP_ID = "d114d003766246b285a94086241e7487";
    private Map<String, Double> rates;
    private static final Map<String, String> currencyMap = new HashMap<>();

    static {
        // Agrega las siglas y nombres de monedas
        currencyMap.put("USD", "USA Dollar");
        currencyMap.put("EUR", "Euro Member Countries");
        currencyMap.put("JPY", "Japan Yen");
        currencyMap.put("AED", "United Arab Emirates Dirham");
        currencyMap.put("AFN", "Afghan Afghani");
        currencyMap.put("ALL", "Albanian Lek");
        currencyMap.put("AMD", "Armenian Dram");
        currencyMap.put("ANG", "Netherlands Antillean Guilder");
        currencyMap.put("AOA", "Angolan Kwanza");
        currencyMap.put("ARS", "Argentine Peso");
        currencyMap.put("AUD", "Australian Dollar");
        currencyMap.put("AWG", "Aruban Florin");
        currencyMap.put("AZN", "Azerbaijani Manat");
        currencyMap.put("BAM", "Bosnia-Herzegovina Convertible Mark");
        currencyMap.put("BBD", "Barbadian Dollar");
        currencyMap.put("BDT", "Bangladeshi Taka");
        currencyMap.put("BGN", "Bulgarian Lev");
        currencyMap.put("BHD", "Bahraini Dinar");
        currencyMap.put("BIF", "Burundian Franc");
        currencyMap.put("BMD", "Bermudan Dollar");
        currencyMap.put("BND", "Brunei Dollar");
        currencyMap.put("BOB", "Bolivian Boliviano");
        currencyMap.put("BRL", "Brazilian Real");
        currencyMap.put("BSD", "Bahamian Dollar");
        currencyMap.put("BTC", "Bitcoin");
        currencyMap.put("BTN", "Bhutanese Ngultrum");
        currencyMap.put("BWP", "Botswanan Pula");
        currencyMap.put("BYN", "Belarusian Ruble");
        currencyMap.put("BYR", "Belarusian Ruble (pre-2016)");
        currencyMap.put("BZD", "Belize Dollar");
        currencyMap.put("CAD", "Canadian Dollar");
        currencyMap.put("CDF", "Congolese Franc");
        currencyMap.put("CHF", "Swiss Franc");
        currencyMap.put("CLF", "Chilean Unit of Account (UF)");
        currencyMap.put("CLP", "Chilean Peso");
        currencyMap.put("CNH", "Chinese Yuan (Offshore)");
        currencyMap.put("CNY", "Chinese Yuan");
        currencyMap.put("COP", "Colombian Peso");
        currencyMap.put("CRC", "Costa Rican Colón");
        currencyMap.put("CUC", "Cuban Convertible Peso");
        currencyMap.put("CUP", "Cuban Peso");
        currencyMap.put("CVE", "Cape Verdean Escudo");
        currencyMap.put("CZK", "Czech Republic Koruna");
        currencyMap.put("DJF", "Djiboutian Franc");
        currencyMap.put("DKK", "Danish Krone");
        currencyMap.put("DOP", "Dominican Peso");
        currencyMap.put("DZD", "Algerian Dinar");
        currencyMap.put("EEK", "Estonian Kroon");
        currencyMap.put("EGP", "Egyptian Pound");
        currencyMap.put("ERN", "Eritrean Nakfa");
        currencyMap.put("ETB", "Ethiopian Birr");
        currencyMap.put("FJD", "Fijian Dollar");
        currencyMap.put("FKP", "Falkland Islands Pound");
        currencyMap.put("GBP", "British Pound Sterling");
        currencyMap.put("GEL", "Georgian Lari");
        currencyMap.put("GGP", "Guernsey Pound");
        currencyMap.put("GHS", "Ghanaian Cedi");
        currencyMap.put("GIP", "Gibraltar Pound");
        currencyMap.put("GMD", "Gambian Dalasi");
        currencyMap.put("GNF", "Guinean Franc");
        currencyMap.put("GTQ", "Guatemalan Quetzal");
        currencyMap.put("GYD", "Guyanaese Dollar");
        currencyMap.put("HKD", "Hong Kong Dollar");
        currencyMap.put("HNL", "Honduran Lempira");
        currencyMap.put("HRK", "Croatian Kuna");
        currencyMap.put("HTG", "Haitian Gourde");
        currencyMap.put("HUF", "Hungarian Forint");
        currencyMap.put("IDR", "Indonesian Rupiah");
        currencyMap.put("ILS", "Israeli New Sheqel");
        currencyMap.put("IMP", "Manx pound");
        currencyMap.put("INR", "Indian Rupee");
        currencyMap.put("IQD", "Iraqi Dinar");
        currencyMap.put("IRR", "Iranian Rial");
        currencyMap.put("ISK", "Icelandic Króna");
        currencyMap.put("JEP", "Jersey Pound");
        currencyMap.put("JMD", "Jamaican Dollar");
        currencyMap.put("JOD", "Jordanian Dinar");
        currencyMap.put("JPY", "Japanese Yen");
        currencyMap.put("KES", "Kenyan Shilling");
        currencyMap.put("KGS", "Kyrgystani Som");
        currencyMap.put("KHR", "Cambodian Riel");
        currencyMap.put("KMF", "Comorian Franc");
        currencyMap.put("KPW", "North Korean Won");
        currencyMap.put("KRW", "South Korean Won");
        currencyMap.put("KWD", "Kuwaiti Dinar");
        currencyMap.put("KYD", "Cayman Islands Dollar");
        currencyMap.put("KZT", "Kazakhstani Tenge");
        currencyMap.put("LAK", "Laotian Kip");
        currencyMap.put("LBP", "Lebanese Pound");
        currencyMap.put("LKR", "Sri Lankan Rupee");
        currencyMap.put("LRD", "Liberian Dollar");
        currencyMap.put("LSL", "Lesotho Loti");
        currencyMap.put("LYD", "Libyan Dinar");
        currencyMap.put("MAD", "Moroccan Dirham");
        currencyMap.put("MDL", "Moldovan Leu");
        currencyMap.put("MGA", "Malagasy Ariary");
        currencyMap.put("MKD", "Macedonian Denar");
        currencyMap.put("MMK", "Myanma Kyat");
        currencyMap.put("MNT", "Mongolian Tugrik");
        currencyMap.put("MOP", "Macanese Pataca");
        currencyMap.put("MRO", "Mauritanian Ouguiya (pre-2018)");
        currencyMap.put("MRU", "Mauritanian Ouguiya");
        currencyMap.put("MTL", "Maltese Lira");
        currencyMap.put("MUR", "Mauritian Rupee");
        currencyMap.put("MVR", "Maldivian Rufiyaa");
        currencyMap.put("MWK", "Malawian Kwacha");
        currencyMap.put("MXN", "Mexican Peso");
        currencyMap.put("MYR", "Malaysian Ringgit");
        currencyMap.put("MZN", "Mozambican Metical");
        currencyMap.put("NAD", "Namibian Dollar");
        currencyMap.put("NGN", "Nigerian Naira");
        currencyMap.put("NIO", "Nicaraguan Córdoba");
        currencyMap.put("NOK", "Norwegian Krone");
        currencyMap.put("NPR", "Nepalese Rupee");
        currencyMap.put("NZD", "New Zealand Dollar");
        currencyMap.put("OMR", "Omani Rial");
        currencyMap.put("PAB", "Panamanian Balboa");
        currencyMap.put("PEN", "Peruvian Nuevo Sol");
        currencyMap.put("PGK", "Papua New Guinean Kina");
        currencyMap.put("PHP", "Philippine Peso");
        currencyMap.put("PKR", "Pakistani Rupee");
        currencyMap.put("PLN", "Polish Zloty");
        currencyMap.put("PYG", "Paraguayan Guarani");
        currencyMap.put("QAR", "Qatari Rial");
        currencyMap.put("RON", "Romanian Leu");
        currencyMap.put("RSD", "Serbian Dinar");
        currencyMap.put("RUB", "Russian Ruble");
        currencyMap.put("RWF", "Rwandan Franc");
        currencyMap.put("SAR", "Saudi Riyal");
        currencyMap.put("SBD", "Solomon Islands Dollar");
        currencyMap.put("SCR", "Seychellois Rupee");
        currencyMap.put("SDG", "Sudanese Pound");
        currencyMap.put("SEK", "Swedish Krona");
        currencyMap.put("SGD", "Singapore Dollar");
        currencyMap.put("SHP", "Saint Helena Pound");
        currencyMap.put("SLL", "Sierra Leonean Leone");
        currencyMap.put("SOS", "Somali Shilling");
        currencyMap.put("SRD", "Surinamese Dollar");
        currencyMap.put("SSP", "South Sudanese Pound");
        currencyMap.put("STD", "São Tomé and Príncipe Dobra (pre-2018)");
        currencyMap.put("STN", "São Tomé and Príncipe Dobra");
        currencyMap.put("SVC", "Salvadoran Colón");
        currencyMap.put("SYP", "Syrian Pound");
        currencyMap.put("SZL", "Swazi Lilangeni");
        currencyMap.put("THB", "Thai Baht");
        currencyMap.put("TJS", "Tajikistani Somoni");
        currencyMap.put("TMT", "Turkmenistani Manat");
        currencyMap.put("TND", "Tunisian Dinar");
        currencyMap.put("TOP", "Tongan Paʻanga");
        currencyMap.put("TRY", "Turkish Lira");
        currencyMap.put("TTD", "Trinidad and Tobago Dollar");
        currencyMap.put("TWD", "New Taiwan Dollar");
        currencyMap.put("TZS", "Tanzanian Shilling");
        currencyMap.put("UAH", "Ukrainian Hryvnia");
        currencyMap.put("UGX", "Ugandan Shilling");
        currencyMap.put("USD", "United States Dollar");
        currencyMap.put("UYU", "Uruguayan Peso");
        currencyMap.put("UZS", "Uzbekistan Som");
        currencyMap.put("VES", "Venezuelan Bolívar Soberano");
        currencyMap.put("VND", "Vietnamese Dong");
        currencyMap.put("VUV", "Vanuatu Vatu");
        currencyMap.put("WST", "Samoan Tala");
        currencyMap.put("XAF", "CFA Franc BEAC");
        currencyMap.put("XAG", "Silver (troy ounce)");
        currencyMap.put("XAU", "Gold (troy ounce)");
        currencyMap.put("XCD", "East Caribbean Dollar");
        currencyMap.put("XDR", "Special Drawing Rights");
        currencyMap.put("XOF", "CFA Franc BCEAO");
        currencyMap.put("XPD", "Palladium Ounce");
        currencyMap.put("XPF", "CFP Franc");
        currencyMap.put("XPT", "Platinum Ounce");
        currencyMap.put("YER", "Yemeni Rial");
        currencyMap.put("ZAR", "South African Rand");
        currencyMap.put("ZMK", "Zambian Kwacha (pre-2013)");
        currencyMap.put("ZMW", "Zambian Kwacha");
    }

    public ConversorDeDivisa() throws IOException {
        fetchRates();
    }

    private void fetchRates() throws IOException {
        URL url = new URL(API_URL + "?app_id=" + APP_ID);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        Gson gson = new Gson();
        Map<String, Object> response = gson.fromJson(reader, new TypeToken<Map<String, Object>>(){}.getType());
        rates = (Map<String, Double>) response.get("rates");
        reader.close();
    }

    public double convert(String fromCurrency, String toCurrency, double amount) {
        double fromRate = rates.get(fromCurrency);
        double toRate = rates.get(toCurrency);
        return (amount / fromRate) * toRate;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void mostrarMonedasDisponibles() {
        System.out.println("Monedas disponibles:");
        for (String currency : rates.keySet()) {
            System.out.println(currency);
        }
    }

    public void mostrarDetallesMonedas() {
        System.out.println("Detalles de las monedas disponibles:");
        for (String currency : rates.keySet()) {
            String details = currencyMap.getOrDefault(currency, "Detalles no disponibles");
            System.out.println(currency + " - " + details);
        }
    }
}

