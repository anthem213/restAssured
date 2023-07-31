package pojo;

import java.util.List;

public class CreateOrderResponse {
    private List<CreateOrderRequest> orders;

    public List<CreateOrderRequest> getOrders() {
        return orders;
    }

    public void setOrders(List<CreateOrderRequest> orders) {
        this.orders = orders;
    }
}
