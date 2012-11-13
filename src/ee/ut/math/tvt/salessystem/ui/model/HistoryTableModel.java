package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;

/**
 * Stock item table model.
 */
public class HistoryTableModel extends SalesSystemTableModel<HistoryItem> {
        private static final long serialVersionUID = 1L;

        private static final Logger log = Logger.getLogger(HistoryTableModel.class);
        
        private List history = new ArrayList();

        public HistoryTableModel() {
                super(new String[] {"Date and time", "Sum of bill"});
        }

        @Override
        protected Object getColumnValue(HistoryItem item, int columnIndex) {
                switch (columnIndex) {
                case 0:
                        return item.getTime();
                case 1:
                        return item.getPrice();
                }
                throw new IllegalArgumentException("Column index out of range");
        }

        public void addItem(final HistoryItem historyItem) {
                rows.add(historyItem);
                log.debug("Entry added to history. Date and time: " + historyItem.getTime() + " - Price: " + historyItem.getPrice());
                fireTableDataChanged();
                
        }

        @Override
        public String toString() {
        	
                final StringBuffer buffer = new StringBuffer();

                for (int i = 0; i < headers.length; i++) {
                        buffer.append(headers[i] + "\t");
                }
                buffer.append("\n");

                for (final HistoryItem stockItem : rows) {
                        buffer.append(stockItem.getTime() + "\t");
                        buffer.append(stockItem.getPrice() + "\t");
                        buffer.append("\n");
                }

                return buffer.toString();
        }
        public void addRow(String row){         
                history.add(row);
        }
        
        public List getHistory(){
                return history;
        }

}