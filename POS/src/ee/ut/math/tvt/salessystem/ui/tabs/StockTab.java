package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;


public class StockTab {

  private JButton addItemButton;

  private SalesSystemModel model;

  public StockTab(SalesSystemModel model) {
    this.model = model;
  }

  public void addItemEventHandler(){
	    //TODO Implement: Open new window with StockAddItemPanel in it.
	  
	  final JDialog dialog = new JDialog();
	  final JTextField itemID = new JTextField();
	  final JTextField itemName = new JTextField();
	  final JTextField itemDescription = new JTextField();
	  final JTextField itemPrice = new JTextField();
	  final JTextField itemQuantity = new JTextField();
	  
	  final JButton addToWarehouse = new JButton("Add Item");
	  final JButton revertWarehouseAdd = new JButton("Cancel");
	  
      dialog.setLayout(new GridLayout(6, 2));
      
      dialog.add(new JLabel("Id"));
      dialog.add(itemID);
      
      dialog.add(new JLabel("Name"));
      dialog.add(itemName);
      
      dialog.add(new JLabel("Description"));
      dialog.add(itemDescription);
      
      dialog.add(new JLabel("Price"));
      dialog.add(itemPrice);
      
      dialog.add(new JLabel("Quantity"));
      dialog.add(itemQuantity);
      
      dialog.add(addToWarehouse);
      dialog.add(revertWarehouseAdd);
      
      dialog.setTitle("Add item to warehouse");
      
      
      dialog.pack();
      dialog.setVisible(true);
      
      addToWarehouse.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              try{
            	  long id;
            	  String name;
            	  String description;
            	  double price;
            	  int quantity;
            	  
            	  id=Long.parseLong(itemID.getText());
            	  name=itemName.getText();
            	  description=itemDescription.getText();
            	  price=Double.parseDouble(itemPrice.getText());
            	  quantity=Integer.parseInt(itemQuantity.getText());
            	  
            	  StockItem item = new StockItem(id, name, description, price, quantity);
            	  
            	  model.getWarehouseTableModel().addItem(item);
            	  
              }catch (Exception e1){
            	  
              }
          }
      });
      
      revertWarehouseAdd.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              dialog.setVisible(false);
            	  dialog.dispose();
          }
      });
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
    
    addItemButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            addItemEventHandler();
        }
    });
    
       
    
    gc.gridwidth = GridBagConstraints.RELATIVE;
    gc.weightx = 1.0;
    panel.add(addItemButton, gc);

    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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

}
