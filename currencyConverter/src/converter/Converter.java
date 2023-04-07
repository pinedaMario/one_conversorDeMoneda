/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package converter;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author pinedaMario
 */
public class Converter {

  //Map<String, Double> metricElement = new HashMap<>();
  private HashMap<String, HashMap<String, Double>> metric;

  public Converter() {
    metric = new HashMap<>();
  }

  public void addMetric(String name) throws ContainsKeyException {
    HashMap<String, Double> metricElement = new HashMap<>();

    if (metric.containsKey(name)) {
      throw new ContainsKeyException("The key " + name + " is already present");
    } else {
      /*
        by default the new metric is added with a convertion factor of 1
      */
      metricElement.put(name, 1.00);
      this.metric.put(name, metricElement);
    }
  }
  
  public void addFactor(String metricName, String factorName, Double factor) throws ContainsKeyException{
    if(!this.metric.containsKey(metricName))
      throw new ContainsKeyException("The metric " + metricName + " isn't available at this moment, pelase add it first");
    
    /*
      if the key is already into the hashmap it will be updated
    */
    HashMap toInsertInto = this.metric.get(metricName);
    toInsertInto.put(factorName, factor);
  }
  
  public Double convert(String metricName, String factorName, Double amount) throws ContainsKeyException{
    if(!this.metric.containsKey(metricName))
      throw new ContainsKeyException("The metric " + metricName + " isn't available at this moment, pelase add it first");
    
    HashMap toGetFrom = this.metric.get(metricName);
    if(!toGetFrom.containsKey(factorName))
      throw new ContainsKeyException("The factor " + factorName + " isn't available at this moment, pelase add it first");
    
    return amount * (double) toGetFrom.get(factorName);
  }
  
  private String[] getStringKeys(HashMap toGetKeys){
    Set<String> keySet = toGetKeys.keySet();
    String[] keys = new String[keySet.size()];
    int i = 0;
    for (String key : keySet) {
        keys[i] = key;
        i++;
    }
    return keys;
  }
  
  public String[] getMetrics(){
    return this.getStringKeys(metric);
  }
  
  public String[] getFactorNames(String metricName) throws ContainsKeyException{
    if(!this.metric.containsKey(metricName))
      throw new ContainsKeyException("The metric " + metricName + " isn't available at this moment, pelase add it first");
    
    return this.getStringKeys(this.metric.get(metricName));
  }
}
