package ir.idpz.taxi.driver.Classes;

public class Request {

    public String origin;

    public String destination;

    public String price;

    public String drivername;

    public String date;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
