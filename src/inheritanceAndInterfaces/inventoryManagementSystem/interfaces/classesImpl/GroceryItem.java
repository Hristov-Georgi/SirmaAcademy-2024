package inheritanceAndInterfaces.inventoryManagementSystem.interfaces.classesImpl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * This class is used to create objects of groceries (food products).
 * It overrides isExpired method that check if current product is expired.
 * Expiration date is formatted to European (Bulgarian) most used data format, which is used in itemsData.txt file.
 */

public class GroceryItem extends InventoryItem {
    DateTimeFormatter EUROPEAN_DATE_FORMAT = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.DAY_OF_MONTH, 2)
            .appendLiteral('.')
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .appendLiteral('.')
            .appendValue(ChronoField.YEAR, 4)
            .toFormatter();

    private LocalDate expirationDate;

    public String getExpirationDate() {
        return this.expirationDate.format(EUROPEAN_DATE_FORMAT);
    }

    public GroceryItem(String category, double price, String name, int id, double quantity, String expirationDate) {
        super(category, price, name, id, quantity);
        this.expirationDate = LocalDate.parse(expirationDate, EUROPEAN_DATE_FORMAT);
    }

    @Override
    public boolean isExpired() {

        if (isPerishable() && expirationDate.isBefore(LocalDate.now())) {
            return true;
        }

        return false;

    }

    @Override
    public String toString() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.##", dfs);


        return String.format("%s, %s, %d, %s, %s",
                super.getCategory(),
                df.format(super.getPrice()),
                super.getId(),
                df.format(super.getQuantity()),
                this.getExpirationDate());
    }

}
