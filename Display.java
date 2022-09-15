import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This class is reponsible for the GUI aspect of this game.
 * @author ajith
 *
 */
public class Display extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

  /**
   * Panel for the gameboard
   */
  private JPanel displayBoardPanel;
  /**
   * Panel for the buttons
   */
  private JPanel displayTileButtonPanel;
  /**
   * GUI representation of the gameboard
   */
  private JButton[][] guiBoard;
  /**
   * GUI representation of the control buttons.
   */
  private JButton[][] tileButtonSetter;
  /**
   * The number of control buttons in the game
   */
  private final int CONTROLBUTTONS = 4;
  /**
   * A list that indicates the state of the buttons
   */
  private ArrayList<Boolean> buttonPress = new ArrayList<>(Arrays.asList(false, false, false));
  
  /**
   * The walling state of the game.
   */
  private boolean secondPressWall = false;  
  /**
   * Current game.
   */
  private Game game;
  /**
   * The current gameboard
   */
  private Board board;

  /**
   * Initialize display
   * @param board The current gameboar
   */
  Display(Board board) {
    this.board = board;
    this.game = new Game(this.board);
    setTitle("A*");
   
    //rendering the game
    displayBoardPanel = new JPanel();
    displayBoardPanel.setBounds(0, 0, 1000, 800);
    displayBoardPanel.setLayout(new GridLayout(board.getBOARDSIZE(), board.getBOARDSIZE()));
    guiBoard = new JButton[board.getBOARDSIZE()][board.getBOARDSIZE()];
    displayTileButtonPanel = new JPanel();
    displayTileButtonPanel.setBounds(0, 800, 1000, 200);
    displayTileButtonPanel.setLayout(new GridLayout(1, CONTROLBUTTONS));
    tileButtonSetter = new JButton[1][CONTROLBUTTONS];
    
    //rendering the buttons and adding event listeners to them
    for (int i = 0; i < 1; i++) {
      for (int j = 0; j < CONTROLBUTTONS; j++) {
        tileButtonSetter[i][j] = new JButton();
        JButton currentGUITile = tileButtonSetter[i][j];
        currentGUITile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        currentGUITile.setBackground(Color.WHITE);
        currentGUITile.addMouseListener(this);
        currentGUITile.addMouseMotionListener(this);
        currentGUITile.addActionListener(this);
        displayTileButtonPanel.add(currentGUITile);
      }
    }
    //rendering the specific details of the buttons
    tileButtonSetter[0][0].setBackground(Color.GRAY);
    tileButtonSetter[0][0].setFont(new Font("Arial", Font.BOLD, 30));
    tileButtonSetter[0][0].setText("Wall Tile");

    tileButtonSetter[0][1].setBackground(Color.GREEN);
    tileButtonSetter[0][1].setFont(new Font("Arial", Font.BOLD, 25));
    tileButtonSetter[0][1].setText("Selected Tile");

    tileButtonSetter[0][2].setBackground(Color.BLUE);
    tileButtonSetter[0][2].setFont(new Font("Arial", Font.BOLD, 30));
    tileButtonSetter[0][2].setText("Path Find");

    tileButtonSetter[0][3].setBackground(Color.RED);
    tileButtonSetter[0][3].setFont(new Font("Arial", Font.BOLD, 30));
    tileButtonSetter[0][3].setText("RESET");

    //rendering the tiles and adding event listeners to them
    for (int i = 0; i < board.getBOARDSIZE(); i++) {
      for (int j = 0; j < board.getBOARDSIZE(); j++) {
        guiBoard[i][j] = new JButton();
        JButton currentGUITile = guiBoard[i][j];
        currentGUITile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        currentGUITile.setBackground(Color.WHITE);
        currentGUITile.addMouseListener(this);
        currentGUITile.addMouseMotionListener(this);
        currentGUITile.addActionListener(this);
        displayBoardPanel.add(currentGUITile);
      }
    }

    add(displayTileButtonPanel);
    add(displayBoardPanel);

    setSize(1000, 1000);
    setLayout(null);
    setVisible(true);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseDragged(MouseEvent e) {
    // TODO Auto-generated method stub

  }
  
  
  /**
   * When the mouse is moving, and the button[0]/wall state is true,
   * the user can turn tiles into walls by moving the mouse.
   */
  @Override
  public void mouseMoved(MouseEvent e) {
    if (buttonPress.get(0)) {
      if (this.secondPressWall) {
        for (int i = 0; i < board.getBOARDSIZE(); i++) {
          for (int j = 0; j < board.getBOARDSIZE(); j++) {
            if (e.getSource().equals(guiBoard[i][j])) {
              if (this.game.placeWallTile(new Position(j, i))) {
                guiBoard[i][j].setBackground(Color.GRAY);
              }
            }
          }
        }
      }
    }
  }

  /**
   * Events when user clicks on the board or controll buttons.
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    
    //When user clicks on wall tile button.
    //It changes the state of wall tile button.
    if (e.getSource().equals(tileButtonSetter[0][0])) {
      if (!(buttonPress.get(0))) {
        buttonPress = new ArrayList<>(Arrays.asList(true, false, false));
      } else {
        buttonPress.set(0, false);
      }
    }
    
    //When user clicks on selected tile button.
    //It changes the state of selected tile button.
    if (e.getSource().equals(tileButtonSetter[0][1])) {
      if (!(buttonPress.get(1))) {
        buttonPress = new ArrayList<>(Arrays.asList(false, true, false));
        System.out.println("PRESSED SELECT");
      } else {
        buttonPress.set(1, false);
      }
    }

    //When user clicks on path find button.
    //It does not changes the state of path find button.
    //It however does call the A* method in board, and
    //colours the path tiles blue.
    if (e.getSource().equals(tileButtonSetter[0][2])) {
      ArrayList<Tile> route = this.game.AStar();
      if (!(route == null)) {
        for (int i = 0; i < board.getBOARDSIZE(); i++) {
          for (int j = 0; j < board.getBOARDSIZE(); j++) {
            for (Tile n : route) {
              if (n.getPos().getX() == j && n.getPos().getY() == i) {
                guiBoard[i][j].setBackground(Color.BLUE);
                System.out.println("BLUE");
              }
            }
          }
        }
      }
    }
    //When user clicks on reset button.
    //It resets the game
    if (e.getSource().equals(tileButtonSetter[0][3])) {
      this.game.reset();
      this.secondPressWall = false;

      for (int i = 0; i < board.getBOARDSIZE(); i++) {
        for (int j = 0; j < board.getBOARDSIZE(); j++) {
          JButton currentGUITile = guiBoard[i][j];
          currentGUITile.setBackground(Color.WHITE);
        }
      }
      buttonPress = new ArrayList<>(Arrays.asList(false, false, false));

    }

    // colours the tile green, if controll buttons is in a selected tile state.
    if (buttonPress.get(1)) {
      for (int i = 0; i < board.getBOARDSIZE(); i++) {
        for (int j = 0; j < board.getBOARDSIZE(); j++) {
          if (e.getSource().equals(guiBoard[i][j])) {
            System.out.println("Pressed Button");
            if (this.game.placeSelectTile(new Position(j, i))) {
              System.out.println("Pressed Valid");
              guiBoard[i][j].setBackground(Color.GREEN);
            }
          }
        }
      }
    }
    
    //When controll button is in a wall state, this here 
    //handles the second click of the user.
    if (buttonPress.get(0)) {
      if (!secondPressWall) {
        for (int i = 0; i < board.getBOARDSIZE(); i++) {
          for (int j = 0; j < board.getBOARDSIZE(); j++) {
            if (e.getSource().equals(guiBoard[i][j])) {
              this.secondPressWall = true;
            }
          }
        }
      } else {
        this.secondPressWall = false;
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub

  }

}
