package atm_machine;

iimport java.util.TreeMap;
import java.util.Collections;
import java.util.Map;

public class Money {
    private Map<Integer, Integer> bills = new TreeMap<>(Collections.reverseOrder());
    /**
     * Constructs Money object with 0 $1, $2, $5, $10, $20, * $50, and $100.
     */
    public Money() {
        bills.put(1, 0);
        bills.put(2, 0);
        bills.put(5, 0);
        bills.put(10, 0);
        bills.put(20, 0);
        bills.put(50, 0);
        bills.put(100, 0);
    }
    /**
     * Constructs Money object using specified numbers of different bills.
     */
    public Money(Map<Integer, Integer> bills) {
        this.bills.putAll(bills);
    }
    /**
     * Updates quantities of specified bill used.
     * @param billValue Value of specified bill.
     * @param quantity  Number of bills used.
     */
    public void updateMoney(int billValue, int quantity) {
        if (bills.containsKey(billValue)) {
            bills.put(billValue, bills.get(billValue) + quantity);
        } else {
            bills.put(billValue, quantity);
        }
    }
    /**
     * Updates quantities of multiple specified bills used.
     * @param bills Map of bills and bill quantities.
     */
    public void updateMoney(Map<Integer, Integer> bills) {
        for (Map.Entry<Integer, Integer> entry : bills.entrySet()) {
            updateMoney(entry.getKey().intValue(), entry.getValue().intValue());
        }
    }
    /**
     * Converts the total quantity of different bills used to a total monetary
     * value.
     * @return Total monetary value of total bills used.
     */
    public double moneyToValue() {
        double total = 0;
        for (Map.Entry<Integer, Integer> entry : bills.entrySet()) {
            total += entry.getKey().intValue() * entry.getValue().intValue();
        }
        return total;
    }
    /**
     * Adds specified monetary value to quantities of different bills used.
     * @param value Monetary value.
     */
    public void valueToMoney(double value) {
        int quotient = 0;
        int billValue = 0;
        for (Map.Entry<Integer, Integer> entry : bills.entrySet()) {
            billValue = entry.getKey().intValue();
            quotient = (int) (value / billValue);
            if (quotient > 0) {
                updateMoney(billValue, quotient);
                value %= billValue;
            }
        }
    }
    /**
     * Returns a string of the collection of the different bills used.
     * @return A string of the collection of the different bills used.
     */
    public String toString() {
        String listing = "";
        for (Map.Entry<Integer, Integer> entry : bills.entrySet()) {
            if (entry.getValue() > 0) {
                listing += entry.getValue().toString() + " $" + entry.getKey().toString() + ", ";
            }
        }
        listing = listing.substring(0, listing.lastIndexOf(','));
        return listing.substring(0, listing.lastIndexOf(", ") + 2) + "and "
                + listing.substring(listing.lastIndexOf(", ") + 2);
    }
}
