package komodocrypto.model.database;

public class OrderStatus {

    int order_id;
    String date;
    boolean complete;
    boolean unsuccessful;
    boolean waiting;
    boolean open;
    boolean close;

    public OrderStatus(int order_id, String date, boolean complete, boolean unsuccessful, boolean waiting, boolean open, boolean close) {
        this.order_id = order_id;
        this.date = date;
        this.complete = complete;
        this.unsuccessful = unsuccessful;
        this.waiting = waiting;
        this.open = open;
        this.close = close;
    }

    public OrderStatus() {
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isUnsuccessful() {
        return unsuccessful;
    }

    public void setUnsuccessful(boolean unsuccessful) {
        this.unsuccessful = unsuccessful;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }
}
