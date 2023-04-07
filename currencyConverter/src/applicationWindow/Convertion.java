/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package applicationWindow;

import converter.Converter;
import converter.ContainsKeyException;

/**
 *
 * @author pinedaMario
 */
public class Convertion {
  private String name;
  private Converter content;

  public Convertion(String name) {
    this.name = name;
    this.content = new Converter();
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the content
   */
  public Converter getContent() {
    return content;
  }

}
