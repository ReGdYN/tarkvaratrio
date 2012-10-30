package ee.ut.math.tvt.salessystem.domain.data;

public class HistoryItem implements Cloneable, DisplayableItem {
    
    private String time;

    private double price;

    public HistoryItem(String time, double price) {
        this.time = time;
        this.price = price;
    }

    public HistoryItem() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String toString() {
        return time + " " + price;
    }

    public Object getColumn(int columnIndex) {
        switch(columnIndex) {
            case 0: 
            	return time;
            case 1: 
            	return new Double(price);
            default: 
            	throw new RuntimeException("Vigane veerg!");
        }
    }
    
    
    public Object clone() {
        HistoryItem item =
            new HistoryItem(getTime(), getPrice());
        return item;
    }

        @Override
        public Long getId() {
                // TODO Auto-generated method stub
                return null;
        }
                
}