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
import java.util.*;
/**
 * Implements the delete command.
 *
 */
public class DeleteCommand extends Command {
  private Vector itemList;
  /**
   * Asks the model for the list of all selected items,
   * so the deletions can be undone if needed.
   */
  public DeleteCommand () {
    itemList = new Vector();
    Enumeration enumeration = model.getSelectedItems();
    while (enumeration.hasMoreElements()) {
      Item item = (Item)(enumeration.nextElement());
      itemList.add(item);
    }
    model.deleteSelectedItems();
  }
  /**
   * Undoes the operation by adding the items to the selected
   * list in the model.
   */
  public boolean undo() {
    Enumeration enumeration = itemList.elements();
    while (enumeration.hasMoreElements()) {
      Item item = (Item) (enumeration.nextElement());
      model.addItem(item);
      model.markSelected(item);
    }
    return true;
  }
  /**
   * Redoes the command  by re-executing it.
   */
  public boolean redo() {
    execute();
    return true;
  }
  /**
   * Re-executes the command.
   */
  public void execute() {
    model.deleteSelectedItems();
  }
}
