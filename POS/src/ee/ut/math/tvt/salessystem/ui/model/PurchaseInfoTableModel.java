package ee.ut.math.tvt.salessystem.ui.model;


import org.apache.log4j.Logger;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;

/**
 * Purchase history details model.
 */
public class PurchaseInfoTableModel extends SalesSystemTableModel<SoldItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(PurchaseInfoTableModel.class);
	
	public PurchaseInfoTableModel() {
		super(new String[] { "Id", "Name", "Price", "Quantity", "Sum"});
	}

	@Override
	protected Object getColumnValue(SoldItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		case 4:
			return item.getQuantity()*item.getPrice();
		}
		throw new IllegalArgumentException("Column index out of range");
	}
	
	public double getTotalSum() {
        double sumTotal = 0.00;
        for (SoldItem i : rows) {
        	sumTotal += i.getQuantity()*i.getPrice();
        }
        return sumTotal;
    }

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final SoldItem item : rows) {
			buffer.append(item.getId() + "\t");
			buffer.append(item.getName() + "\t");
			buffer.append(item.getPrice() + "\t");
			buffer.append(item.getQuantity() + "\t");
			buffer.append(item.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
	
    /**
     * Add new StockItem to table.
     */
    
	public void addItem(final SoldItem item) {
        rows.add(item);
        log.debug("Added: " + item.getName() + " | Quantity: " + item.getQuantity());
        fireTableDataChanged();
    }
}
