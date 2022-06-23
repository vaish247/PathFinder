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

public class Display extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

  private JPanel displayBoardPanel;
  private JPanel displayTileButtonPanel;
  private JButton[][] guiBoard;
  private JButton[][] tileButtonSetter;
  private final int CONTROLBUTTONS = 4;
  private ArrayList<Boolean> buttonPress = new ArrayList<>(Arrays.asList(false, false, false));
  private boolean secondPressWall = false;
  private boolean secondPressERASE = false;
  private int buttonCount = 0;
  private Game game;

  private Board board;

  Display(Board board) {
    this.board = board;
    this.game = new Game(this.board);
    setTitle("A*");
    displayBoardPanel = new JPanel();
    displayBoardPanel.setBounds(0, 0, 1000, 800);
    displayBoardPanel.setLayout(new GridLayout(board.getBOARDSIZE(), board.getBOARDSIZE()));
    guiBoard = new JButton[board.getBOARDSIZE()][board.getBOARDSIZE()];

    displayTileButtonPanel = new JPanel();
    displayTileButtonPanel.setBounds(0, 800, 1000, 200);
    displayTileButtonPanel.setLayout(new GridLayout(1, CONTROLBUTTONS));
    tileButtonSetter = new JButton[1][CONTROLBUTTONS];

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

  @Override
  public void mouseClicked(MouseEvent e) {
    // TODO Auto-generated method stub
    if (e.getSource().equals(tileButtonSetter[0][0])) {
      if (!(buttonPress.get(0))) {
        buttonPress = new ArrayList<>(Arrays.asList(true, false, false));
      } else {
        buttonPress.set(0, false);
      }
    }
    if (e.getSource().equals(tileButtonSetter[0][1])) {
      if (!(buttonPress.get(1))) {
        buttonPress = new ArrayList<>(Arrays.asList(false, true, false));
        System.out.println("PRESSED SELECT");
      } else {
        buttonPress.set(1, false);
      }
    }

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
    if (e.getSource().equals(tileButtonSetter[0][3])) {
      this.game.reset();
      this.secondPressERASE = false;
      this.secondPressWall = false;

      for (int i = 0; i < board.getBOARDSIZE(); i++) {
        for (int j = 0; j < board.getBOARDSIZE(); j++) {
          JButton currentGUITile = guiBoard[i][j];
          currentGUITile.setBackground(Color.WHITE);
        }
      }
      buttonPress = new ArrayList<>(Arrays.asList(false, false, false));

    }

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
