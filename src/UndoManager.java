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
 * The undo manager essentially supports undo and redo
 * operations. In addition, it has a command to begin and end 
 * commands.
 * @author mh6624pa
 *
 */
public class UndoManager {
  private static Model model;
  private Stack history;
  private Stack future;
  private static UndoManager manager;
  private Command currentCommand;
  /**
   * Sets up the stacks.
   */
  private UndoManager() {
    history = new Stack();
    future = new Stack();
  }
  /**
   * For the singleton pattern
   * @return the instance
   */
  public static UndoManager instance() {
    if (manager == null) {
      manager = new UndoManager();
    }
    return manager;
  }
  /**
   * sets the model
   * @param model the model
   */
  public static void setModel(Model model) {
    UndoManager.model = model;
  }
  /**
   * Pushes a command onto the history stack.
   * If a previous command is unfinished, it is ended first.
   * The redo stack (future) is cleared and the command is executed.
   * @param command
   */
  public void beginCommand(Command command) {
    if (currentCommand != null) {
       currentCommand.end();
       history.push(currentCommand);
    }
    currentCommand = command;
    future.clear();
    command.execute();
  }
  /**
   * Normal end of a command. The command is ended and pushed
   * onto the history stack.
   * @param command
   */
  public void endCommand(Command command) {
    command.end();
    history.push(command);
    currentCommand = null;
    model.setChanged();
  }
  /**
   * Undoes the command by calling undo on the Command object.
   * The command is moved to the redo stack (future).
   */
  public void undo() {
    if (!(history.empty())) {
      Command command = (Command) (history.peek());
      if (command.undo()) {
        history.pop();
        future.push(command);
      }
    }
  }
  /**
   * Redoes the command by calling redo on the Command object
   * that is on top of the redo stack (future). The command is
   * then pushed onto the history stack.
   */
  public void redo() {
    if (!(future.empty())) {
      Command command = (Command)(future.peek());
      if (command.redo()) {
        future.pop();
        history.push(command);
      }
    }
  }
}