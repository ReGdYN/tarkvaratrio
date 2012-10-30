package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;


public class StockTab {

  private JButton addItemButton;

  private SalesSystemModel model;

  public StockTab(SalesSystemModel model) {
    this.model = model;
  }

  // warehouse stock tab - consists of a menu and a table
  public Component draw() {
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    GridBagLayout gb = new GridBagLayout();
    GridBagConstraints gc = new GridBagConstraints();
    panel.setLayout(gb);

    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 0d;

    panel.add(drawStockMenuPane(), gc);

    gc.weighty = 1.0;
    gc.fill = GridBagConstraints.BOTH;
    panel.add(drawStockMainPane(), gc);
    return panel;
  }

  // warehouse menu
  private Component drawStockMenuPane() {
    JPanel panel = new JPanel();

    GridBagConstraints gc = new GridBagConstraints();
    GridBagLayout gb = new GridBagLayout();

    panel.setLayout(gb);

    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weightx = 0;

    addItemButton = new JButton("Add");
    gc.gridwidth = GridBagConstraints.RELATIVE;
    gc.weightx = 1.0;
    panel.add(addItemButton, gc);

    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    
  addItemButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
		addStockItemEventHandler();
	}
	});
    
    return panel;
  }


  // table of the wareshouse stock
  private Component drawStockMainPane() {
    JPanel panel = new JPanel();

    JTable table = new JTable(model.getWarehouseTableModel());

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

    panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
    
    return panel;
  }
  
  
  public void addStockItemEventHandler() {
	  final JTextField StockIDField = new JTextField();
	  final JTextField StockNameField = new JTextField();
	  final JTextField StockDescriptionField = new JTextField();
	  final JTextField StockPriceField = new JTextField();
	  final JTextField StockQuantityField = new JTextField();
	  
	  JButton CancelButton=new JButton("Cancel");
	  JButton AddItemButton=new JButton("Add item");
	  
	  JLabel StockIDLabel = new JLabel("ID");
	  JLabel StockNameLabel = new JLabel("Name");
	  JLabel StockDescriptionLabel = new JLabel("Description");
	  JLabel StockPriceLabel = new JLabel("Price");
	  JLabel StockQuantityLabel = new JLabel("Quantity");
	  GridBagConstraints constrain= new GridBagConstraints();
	  
	  
	  
	  final JFrame stock= new JFrame("Enter item data");
	  stock.setSize(500,100);
	  constrain.weightx=1.0;
	  constrain.weighty=1.0;
	  constrain.fill=GridBagConstraints.HORIZONTAL;
	  
	  GridBagLayout gblayout = new GridBagLayout();
	  stock.setLayout(gblayout);
	  constrain.gridx=0;
	  constrain.gridy=0;
	  stock.getContentPane().add(StockIDLabel,constrain);
	  constrain.gridx=1;
	  stock.getContentPane().add(StockNameLabel,constrain);
	  constrain.gridx=2;
	  stock.getContentPane().add(StockDescriptionLabel,constrain);
	  constrain.gridx=3;
	  stock.getContentPane().add(StockPriceLabel,constrain);
	  constrain.gridx=4;
	  stock.getContentPane().add(StockQuantityLabel,constrain);
	  
	  constrain.gridy=1;
	  constrain.gridx=0;
	  stock.getContentPane().add(StockIDField,constrain);
	  constrain.gridx=1;
	  stock.getContentPane().add(StockNameField,constrain);
	  constrain.gridx=2;
	  stock.getContentPane().add(StockDescriptionField,constrain);
	  constrain.gridx=3;
	  stock.getContentPane().add(StockPriceField,constrain);
	  constrain.gridx=4;
	  stock.getContentPane().add(StockQuantityField,constrain);
	  
	  constrain.gridx=0;
	  constrain.gridy=2;
	  constrain.gridwidth=2;
	  stock.getContentPane().add(CancelButton,constrain);
	  constrain.gridx=3;
	  stock.getContentPane().add(AddItemButton,constrain);
	  Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	  stock.setLocation((screen.width - 500) / 2, (screen.height - 100) / 2);
	  stock.setVisible(true);
	  
	  CancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				stock.dispose();
		}});
	  
	  AddItemButton.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e) {
			try {
				if (StockIDField.getText().isEmpty()==false &&
						StockNameField.getText().isEmpty()==false &&
						StockDescriptionField.getText().isEmpty()==false &&
						StockPriceField.getText().isEmpty()==false &&
						StockQuantityField.getText().isEmpty()==false) {
					
					long ID = Long.parseLong(StockIDField.getText());
					String Name = StockNameField.getText();
					String Description = StockDescriptionField.getText();
					double Price = Double.parseDouble(StockPriceField.getText());
					int Amount = Integer.parseInt(StockQuantityField.getText());
					StockItem item = new StockItem(ID,Name,Description,Price,Amount);
					model.getWarehouseTableModel().addItem(item);
					stock.dispose();
					return;
				}
				JOptionPane.showMessageDialog(stock,"Fill all fields","Text error",JOptionPane.ERROR_MESSAGE);
					
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(stock,"Incorrect characters","Text error",JOptionPane.ERROR_MESSAGE);
				}
		  }
	  }); 		 
  }
  

}