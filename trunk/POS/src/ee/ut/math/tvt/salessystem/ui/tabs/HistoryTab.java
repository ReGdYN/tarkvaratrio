package ee.ut.math.tvt.salessystem.ui.tabs;

import org.apache.log4j.Logger;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;


public class HistoryTab {
    
        private static final Logger log = Logger.getLogger(HistoryTab.class);
        
        private SalesSystemModel model;

    public HistoryTab(SalesSystemModel model) {
        this.model = model;
    } 
    
    public Component draw() {
        JPanel panel = new JPanel();
        final JTable table = new JTable(model.getHistoryTableModel());
        table.addMouseListener(new MouseListener(){
        	
        @Override
        public void mouseClicked(MouseEvent arg0) {}

        @Override
        public void mouseEntered(MouseEvent arg0) {}

        @Override
        public void mouseExited(MouseEvent arg0) {}

        @Override
        public void mousePressed(MouseEvent arg0) {}

        @Override
        public void mouseReleased(MouseEvent arg0) {
                String history = "" + model.getHistoryTableModel().getHistory().get(table.getSelectedRow());        
                log.debug(history);
                JOptionPane.showMessageDialog(null,"" + history, "Purchase info", JOptionPane.PLAIN_MESSAGE);
        }
                
		});
	        
	    JTableHeader header = table.getTableHeader();
	    header.setReorderingAllowed(false);
	    
	    JScrollPane scrollPane = new JScrollPane(table);
	
	    GridBagConstraints gc = new GridBagConstraints();
	    GridBagLayout gb = new GridBagLayout();
	    gc.fill = GridBagConstraints.BOTH;
	    gc.weightx = 1.0;
	    gc.weighty = 1.0;
	
	    panel.setLayout(gb);
	    panel.add(scrollPane, gc);
	
	    panel.setBorder(BorderFactory.createTitledBorder("History"));
	    
	    return panel;
    }
}