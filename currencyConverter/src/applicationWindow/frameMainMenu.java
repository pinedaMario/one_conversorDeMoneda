/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package applicationWindow;

import converter.Converter;
import converter.ContainsKeyException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author pinedaMario
 */
public class frameMainMenu extends javax.swing.JFrame {

  private Convertion [] types;
  private String [] typeNames;
  private int selection;
  private String selectedSourceCB;
  private Double quantityToConvert;
  private String selectedFactorCB;
  /*
    Instantiate the types array and it's names  
  */
  private void convertionTypes(){
    // two positions to manage currencies and temperature
    this.types = new Convertion[2];
    
    /*
      initialize the class with the specific type
      due the initial problem is to convert currencies it is being to be the
      first one at the array
    */
    types[0] = new Convertion("Currency");
    types[1] = new Convertion("Distance");
  }
  
  /*
    Fill the currency factors to use on convertions
  */
  private void currencyFactors(){
    Converter currency = types[0].getContent();
    try {
      currency.addMetric("Quetzal");
      currency.addMetric("Dollar");
      currency.addMetric("Euro");
      currency.addMetric("Pound sterling");
      currency.addMetric("Japanese Yen");
      currency.addMetric("South Korean won");
      
      currency.addFactor("Quetzal", "Dollar", 0.13);
      currency.addFactor("Quetzal", "Euro", 0.12);
      currency.addFactor("Quetzal", "Pound sterling", 0.10);
      currency.addFactor("Quetzal", "Japanese Yen", 16.89);
      currency.addFactor("Quetzal", "South Korean won", 168.70);
      
      currency.addFactor("Dollar", "Quetzal", 7.79);
      currency.addFactor("Euro", "Quetzal", 8.52);
      currency.addFactor("Pound sterling", "Quetzal", 9.71);
      currency.addFactor("Japanese Yen", "Quetzal", 0.059);
      currency.addFactor("South Korean won", "Quetzal", 0.0059);

    } catch (ContainsKeyException except){
      String message = "Gotten message: \"" + except.getMessage() + "\", please fixit and restart the application";
      JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
  
    /*
    Fill the distance factors to use on convertions
  */
  private void temperatureFactors(){
    Converter distance = types[1].getContent();
    try {
      distance.addMetric("Meters");
      distance.addMetric("Kilometre");
      distance.addMetric("Centimeter");
      distance.addMetric("Mile");
      distance.addMetric("Yard");
      distance.addMetric("Foot");
      
      distance.addFactor("Meters", "Kilometre", 0.001);
      distance.addFactor("Meters", "Centimeter", 100.0);
      distance.addFactor("Meters", "Mile", 0.000621371);
      distance.addFactor("Meters", "Yard", 1.09361);
      distance.addFactor("Meters", "Foot", 3.28084);
      
      distance.addFactor("Kilometre", "Meters", 1000.00);
      distance.addFactor("Centimeter", "Meters", 0.01);
      distance.addFactor("Mile", "Meters", 1609.34);
      distance.addFactor("Yard", "Meters", 0.9144);
      distance.addFactor("Foot", "Meters", 0.3048);

    } catch (ContainsKeyException except){
      String message = "Gotten message: \"" + except.getMessage() + "\", please fixit and restart the application";
      JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  private void sourceMetrics(){
    String[] metrics = types[selection].getContent().getMetrics();
    
    JComboBox<String> CBMetricsOptions = new JComboBox<>(metrics);
    
    JOptionPane.showOptionDialog(this, CBMetricsOptions, "Selecciona una opción", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
    selectedSourceCB = CBMetricsOptions.getSelectedItem().toString();
    
    sourceQuantity();
  }
  
  private void sourceQuantity(){
    boolean isValidNumber = false;
    
    do{
      try{
        String inputValue = JOptionPane.showInputDialog(this, "quantity to convert:",null, JOptionPane.PLAIN_MESSAGE);
        if (inputValue == null) return;
        this.quantityToConvert = Double.valueOf(inputValue);
        isValidNumber = true;

      } catch(NumberFormatException except) {
        JOptionPane.showMessageDialog(null, "can't Cast the input into Double, please try again", "Error", JOptionPane.ERROR_MESSAGE);
      }
    } while (!isValidNumber);
    
    goalFactors();
    
  }
  
  private void goalFactors(){
    try{
      String[] metrics = types[selection].getContent().getFactorNames(selectedSourceCB);
      
      JComboBox<String> CBFactorsOptions = new JComboBox<>(metrics);

      JOptionPane.showOptionDialog(this, CBFactorsOptions, "Selecciona una opción", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
      this.selectedFactorCB = CBFactorsOptions.getSelectedItem().toString();
      
      System.out.println("selected factor: " + this.selectedFactorCB);
      showConvertion();
    } catch(ContainsKeyException except) {
      JOptionPane.showMessageDialog(null, "Returned message: \"" + except.getMessage() + "\". Please address it first.", "Error", JOptionPane.ERROR_MESSAGE);
    } 
  }
  
  private void showConvertion(){
    try{
      double convertion = types[selection].getContent().convert(selectedSourceCB, selectedFactorCB, quantityToConvert);
      convertion = Math.round(convertion * 100);
      convertion /= 100;
      JOptionPane.showMessageDialog(this, "" + quantityToConvert + " " + selectedSourceCB + " in " + selectedFactorCB + " are " + convertion, "Error", JOptionPane.PLAIN_MESSAGE);
    } catch (ContainsKeyException except){
      JOptionPane.showMessageDialog(null, "gotten message: \"" + except.getMessage() + "\", please address it first", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /*
    get the convertion type names to be used at the combobox
  */
  private void fillTypeNames(){
    this.typeNames = new String[this.types.length];
    for (int i = 0; i<this.types.length; i++){
      this.typeNames[i] = this.types[i].getName();
    }
  }
  
  private void redefineCB(){
    //CBConvertionOptions = new JComboBox(this.typeNames);
    CBConvertionOptions.setModel(new javax.swing.DefaultComboBoxModel<>(this.typeNames));
    
    CBConvertionOptions.addActionListener((ActionEvent e) -> {
      selection = CBConvertionOptions.getSelectedIndex();
      sourceMetrics();
    });
  }
  
  
  /**
   * Creates new form frameMainMenu
   */
  public frameMainMenu() {
    convertionTypes();
    currencyFactors();
    temperatureFactors();
    
    fillTypeNames();
    
    initComponents();
    
    redefineCB();
    
    this.setTitle("MultiConverter");
  }
  
  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    mainPanel = new javax.swing.JPanel();
    labelTitle = new javax.swing.JLabel();
    labelMessage = new javax.swing.JLabel();
    CBConvertionOptions = new javax.swing.JComboBox<>();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    labelTitle.setText("Converter");

    labelMessage.setText("Please select the desired convertion");

    CBConvertionOptions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

    javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
    mainPanel.setLayout(mainPanelLayout);
    mainPanelLayout.setHorizontalGroup(
      mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(mainPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(CBConvertionOptions, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(mainPanelLayout.createSequentialGroup()
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(labelTitle)
              .addComponent(labelMessage))
            .addGap(0, 165, Short.MAX_VALUE)))
        .addContainerGap())
    );
    mainPanelLayout.setVerticalGroup(
      mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(mainPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(labelTitle)
        .addGap(18, 18, 18)
        .addComponent(labelMessage)
        .addGap(18, 18, 18)
        .addComponent(CBConvertionOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(18, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(frameMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(frameMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(frameMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(frameMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new frameMainMenu().setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JComboBox<String> CBConvertionOptions;
  private javax.swing.JLabel labelMessage;
  private javax.swing.JLabel labelTitle;
  private javax.swing.JPanel mainPanel;
  // End of variables declaration//GEN-END:variables
}
