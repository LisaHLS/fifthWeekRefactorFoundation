package practice2;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {

    public Receipt() {
        tax = new BigDecimal(0.1);
        tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal tax;

    public double CalculateGrandTotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotalReducedPrice = getSubTotalHasReducedPrice(products, items);
        BigDecimal taxTotal = subTotalReducedPrice.multiply(tax);
        BigDecimal grandTotal = subTotalReducedPrice.add(taxTotal);

        return grandTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private BigDecimal getOrderItemTotalPriceByProduct(Product product, OrderItem orderItem) {
        BigDecimal orderItemTotal = product.getPrice().multiply(new BigDecimal(orderItem.getCount()));
        return orderItemTotal;
    }

    private BigDecimal getOrderItemReducedPriceByProduct(Product product, OrderItem orderItem) {
        BigDecimal OrderItemReducedPrice = product.getPrice()
            .multiply(product.getDiscountRate())
            .multiply(new BigDecimal(orderItem.getCount()));
        return OrderItemReducedPrice;
    }

    private BigDecimal getSubTotalHasReducedPrice(List<Product> products, List<OrderItem> items) {

        BigDecimal subTotalReducedPrice = new BigDecimal(0);
        for (Product product : products) {
            OrderItem curItem = findOrderItemByProduct(items, product);
            subTotalReducedPrice = subTotalReducedPrice.add(getOrderItemTotalPriceByProduct(product, curItem));
            subTotalReducedPrice = subTotalReducedPrice.subtract(getOrderItemReducedPriceByProduct(product, curItem));
        }
        return subTotalReducedPrice;
    }

    private OrderItem findOrderItemByProduct(List<OrderItem> items, Product product) {
        OrderItem curItem = null;
        for (OrderItem item : items) {
            if (item.getCode() == product.getCode()) {
                curItem = item;
                break;
            }
        }
        return curItem;
    }
}
