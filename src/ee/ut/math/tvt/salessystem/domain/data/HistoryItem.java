package ee.ut.math.tvt.salessystem.domain.data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

@Entity
@Table(name = "HISTORYITEM")
public class HistoryItem implements Cloneable, DisplayableItem {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column (name="date")
    private Timestamp time;
    
    @Column(name="TotalSum")
    private BigDecimal price;

    @Transient
    private List<SoldItem> items;
    
    public HistoryItem(long id, List<SoldItem> items) {
    	time = new Timestamp((long)(new Date().getTime()));
        time.setNanos(0);
        this.items = items;
        this.id = id;
        PurchaseInfoTableModel model = new PurchaseInfoTableModel();
        model.populateWithData(items);
        this.price = new BigDecimal(model.getTotalSum());
    }

    public HistoryItem() {
    }

    public Timestamp getTime() {
        return time;
    }

    public BigDecimal getPrice() {
        return price;
    }
    
    public String toString() {
        return time + " " + price;
    }
    
    public List<SoldItem> getItems() {
        return items;
    }

        @Override
        public Long getId() {
                // TODO Auto-generated method stub
                return id;
        }
                
}