package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "Point-of-sale" in the menu).
 */
public class PurchaseTab {

  private static final Logger log = Logger.getLogger(PurchaseTab.class);

  private final SalesDomainController domainController;

  private JButton newPurchase;

  private JButton submitPurchase;

  private JButton cancelPurchase;

  private PurchaseItemPanel purchasePane;

  private SalesSystemModel model;
  
  private final Session session;


  public PurchaseTab(SalesDomainController controller, SalesSystemModel model) {
    this.domainController = controller;
    this.model = model;
    this.session = this.domainController.getSession();
  }


  /**
   * The purchase tab. Consists of the purchase menu, current purchase dialog and
   * shopping cart table.
   */
  public Component draw() {
    JPanel panel = new JPanel();

    // Layout
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    panel.setLayout(new GridBagLayout());

    // Add the purchase menu
    panel.add(getPurchaseMenuPane(), getConstraintsForPurchaseMenu());

    // Add the main purchase-panel
    purchasePane = new PurchaseItemPanel(model);
    panel.add(purchasePane, getConstraintsForPurchasePanel());

    return panel;
  }




  // The purchase menu. Contains buttons "New purchase", "Submit", "Cancel".
  private Component getPurchaseMenuPane() {
    JPanel panel = new JPanel();

    // Initialize layout
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gc = getConstraintsForMenuButtons();

    // Initialize the buttons
    newPurchase = createNewPurchaseButton();
    submitPurchase = createConfirmButton();
    cancelPurchase = createCancelButton();

    // Add the buttons to the panel, using GridBagConstraints we defined above
    panel.add(newPurchase, gc);
    panel.add(submitPurchase, gc);
    panel.add(cancelPurchase, gc);

    return panel;
  }


  // Creates the button "New purchase"
  private JButton createNewPurchaseButton() {
    JButton b = new JButton("New purchase");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newPurchaseButtonClicked();
      }
    });

    return b;
  }

  // Creates the "Confirm" button
  private JButton createConfirmButton() {
    JButton b = new JButton("Confirm");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        submitPurchaseButtonClicked();
      }
    });
    b.setEnabled(false);

    return b;
  }


  // Creates the "Cancel" button
  private JButton createCancelButton() {
    JButton b = new JButton("Cancel");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelPurchaseButtonClicked();
      }
    });
    b.setEnabled(false);

    return b;
  }





  /* === Event handlers for the menu buttons
   *     (get executed when the buttons are clicked)
   */


  /** Event handler for the <code>new purchase</code> event. */
  protected void newPurchaseButtonClicked() {
    log.info("New sale process started");
    try {
      domainController.startNewPurchase();
      startNewSale();
    } catch (VerificationFailedException e1) {
      log.error(e1.getMessage());
    }
  }


  /**  Event handler for the <code>cancel purchase</code> event. */
  protected void cancelPurchaseButtonClicked() {
    log.info("Sale cancelled");
    try {
      domainController.cancelCurrentPurchase();
      endSale();
      model.getCurrentPurchaseTableModel().clear();
    } catch (VerificationFailedException e1) {
      log.error(e1.getMessage());
    }
  }


  /** Event handler for the <code>submit purchase</code> event. */
  protected void submitPurchaseButtonClicked() {
	  if (model.getCurrentPurchaseTableModel().getRowCount()==0) {
          JOptionPane.showMessageDialog(null,"No items in basket","Invalid Purchase", JOptionPane.ERROR_MESSAGE);
          return;
	  }
		log.info("Sale complete");

		double sumToPay = -1;
		double sumOfPurchase = 0;
		double sumToReturn;

		for (int i = 0; i < model.getCurrentPurchaseTableModel().getTableRows().size(); i++) {
			sumOfPurchase += model.getCurrentPurchaseTableModel().getTableRows().get(i).getPrice() * model.getCurrentPurchaseTableModel().getTableRows().get(i).getQuantity();
			
		} try {
			sumToPay = Double.parseDouble(JOptionPane.showInputDialog("Balance: " + sumOfPurchase + "\nPayed:  "));			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Invalid character, numbers only.","Payment error",JOptionPane.ERROR_MESSAGE);
		}	
		
		sumToReturn = sumToPay - sumOfPurchase;
		
		if (sumToReturn < 0) {
			JOptionPane.showMessageDialog(null,"Insufficient funds","Payment error",JOptionPane.ERROR_MESSAGE);
		}
		
		while(sumToReturn < 0){
			sumToPay = Double.parseDouble(JOptionPane.showInputDialog("The sum is: " + sumOfPurchase + "\nEnter payment sum:  "));	
			sumToReturn = sumToPay - sumOfPurchase;
			if (sumToReturn < 0) {
				JOptionPane.showMessageDialog(null,"The payment is not enough","Payment error",JOptionPane.ERROR_MESSAGE);	
		
			}
		}
		int intToReturn = (int)(sumToReturn*100.0);
		int value = JOptionPane.showConfirmDialog(null,"The return sum: " + intToReturn/100.0 + "\nPress OK to confirm \nPress Cancel to exit", "Return sum", JOptionPane.OK_CANCEL_OPTION);
		int nextId = model.getHistoryTableModel().getRowCount()+1;
		try {
			if (value == 0){
				HistoryItem newItem = new HistoryItem(nextId, model.getCurrentPurchaseTableModel().getTableRows());
		        model.getHistoryTableModel().addItem(newItem);
		        Transaction tx = session.beginTransaction();
		        session.save(newItem);
		        tx.commit();
		        tx = session.beginTransaction();
		        for (SoldItem i : newItem.getItems()) {
			        i.setHistoryItem(newItem);
			        session.save(i);
		        }
		        tx.commit();
		        
		        //model.getHistoryTableModel().addItem(new HistoryItem(nextId, sumOfPurchase));
		        
		        endSale();
		        model.getCurrentPurchaseTableModel().clear();
				for (int i = 0; i < model.getCurrentPurchaseTableModel().getTableRows().size(); i++) {
					StockItem stock = model.getWarehouseTableModel().getItemById(model.getCurrentPurchaseTableModel().getTableRows().get(i).getId());
					stock.setQuantity(stock.getQuantity()- model.getCurrentPurchaseTableModel().getTableRows().get(i).getQuantity());
				}
				endSale();
				 log.debug("Contents of the current bill:\n" + model.getCurrentPurchaseTableModel());
				 model.getHistoryTableModel().addRow((""+model.getCurrentPurchaseTableModel()).replaceAll("\t", "    "));
				 model.getCurrentPurchaseTableModel().clear();
				 
				domainController.submitCurrentPurchase(model.getCurrentPurchaseTableModel().getTableRows());
			}
			else if (value == 2){
				log.info("Cancelled payment");
			}	
			
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}


  /* === Helper methods that bring the whole purchase-tab to a certain state
   *     when called.
   */

  // switch UI to the state that allows to proceed with the purchase
  private void startNewSale() {
    purchasePane.reset();

    purchasePane.setEnabled(true);
    submitPurchase.setEnabled(true);
    cancelPurchase.setEnabled(true);
    newPurchase.setEnabled(false);
  }

  // switch UI to the state that allows to initiate new purchase
  private void endSale() {
    purchasePane.reset();

    cancelPurchase.setEnabled(false);
    submitPurchase.setEnabled(false);
    newPurchase.setEnabled(true);
    purchasePane.setEnabled(false);
  }




  /* === Next methods just create the layout constraints objects that control the
   *     the layout of different elements in the purchase tab. These definitions are
   *     brought out here to separate contents from layout, and keep the methods
   *     that actually create the components shorter and cleaner.
   */

  private GridBagConstraints getConstraintsForPurchaseMenu() {
    GridBagConstraints gc = new GridBagConstraints();

    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 0d;

    return gc;
  }


  private GridBagConstraints getConstraintsForPurchasePanel() {
    GridBagConstraints gc = new GridBagConstraints();

    gc.fill = GridBagConstraints.BOTH;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 1.0;

    return gc;
  }


  // The constraints that control the layout of the buttons in the purchase menu
  private GridBagConstraints getConstraintsForMenuButtons() {
    GridBagConstraints gc = new GridBagConstraints();

    gc.weightx = 0;
    gc.anchor = GridBagConstraints.CENTER;
    gc.gridwidth = GridBagConstraints.RELATIVE;

    return gc;
  }
  	private static final String DATE_FORMAT_NOW = "dd.MM.yyyy HH:mm:ss";

	public static String currentTime() {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(calendar.getTime());
	}
}
