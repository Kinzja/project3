/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 
 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.  
 */
import java.awt.*;
import java.util.*;
/**
 * Makes the select command undoable and redoable
 *
 */
public class SelectCommand extends Command {
  private Item item;
  /**
   * Gets the point that may fall in the shape.
   * Searches the model for a possible item that
   * may be selected
   * @param point a point that may be inside some item
   */
  public void setPoint(Point point) {
    Enumeration enumeration = model.getItems();
    while (enumeration.hasMoreElements()) {
      item = (Item)(enumeration.nextElement());
      if (item.includes(point)) {
        model.markSelected(item);
        break;
      }
    }
  }
  /**
   * Undoes the selection by having the model unselect
   * the item
   */
  public boolean undo() {
    model.unSelect(item);
    return true;
  }
  /**
   * Undoes the selection by having the model select
   * the item
   */
  public boolean  redo() {
    execute();
    return true;
  }
  /**
   * Executes the selection by having the model select
   * the item
   */
  public void execute() {
    model.markSelected(item);
  }
}
