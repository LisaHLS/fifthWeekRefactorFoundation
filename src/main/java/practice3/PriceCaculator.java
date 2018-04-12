package practice3;

import java.math.BigDecimal;

public class PriceCaculator {
    private final Order order;

    public PriceCaculator(Order order) {
        this.order = order;
    }

    public BigDecimal calculate() {
        BigDecimal subTotal = getTotalPrice().subtract(getDiscounts());
        BigDecimal taxTotal = subTotal.multiply(order.getTax());
        BigDecimal grandTotal = subTotal.add(taxTotal);

        return grandTotal;
    }

    private BigDecimal getDiscounts() {
        BigDecimal result = new BigDecimal(0);
        for (BigDecimal discount : order.getDiscounts()) {
            result = result.add(discount);
        }
        return result;
    }

    private BigDecimal getTotalPrice() {
        BigDecimal result = new BigDecimal(0);
        for (OrderLineItem lineItem : order.getOrderLineItemList()) {
            result = result.add(lineItem.getPrice());
        }
        return result;
    }
}
