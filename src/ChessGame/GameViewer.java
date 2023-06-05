package ChessGame;

import Controller.Controller;
import ChessGame.Images.Images;
import ChessModel.Pieces.Pair;
import ChessModel.Pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class GameViewer extends JPanel {
    public int mouseX, mouseY;//Mouse position
    public boolean pause;//if game is paused
    private Images pieces;//Image class
    private int xDimensions, yDimensions;
    private Controller controller;
    private static JFrame frame; // The frame on which the board is displayed
    private static JFrame info;
    private static JFrame scoreBoard;
    private String p1Name;
    private String p2Name;
    private int p1Score;
    private int p2Score;
    private Vector<Pair> moves;

    public GameViewer(Controller c) {
        initializeBoard();
        controller = c;
        p1Score = 0;
        p2Score = 0;

        // Display the board to the screen.
        setupFrame();

        //get Names from players
        setupNames();
    }

    private void setupNames() {
        info = new JFrame("Enter Names");
        info.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel p1 = new JLabel("Player 1: ");
        final JLabel p2 = new JLabel("Player 2: ");

        final JTextField player1 = new JTextField("Name", 10);
        final JTextField player2 = new JTextField("Name", 10);

        Container content = info.getContentPane();
        SpringLayout layout = new SpringLayout();
        content.setLayout(layout);

        Dimension s1 = player1.getPreferredSize();
        Dimension s2 = player2.getPreferredSize();
        player1.setBounds(100, 20, s1.width, s1.height);
        player2.setBounds(100, 50, s2.width, s2.height);
        content.add(p1);
        content.add(player1);
        content.add(p2);
        content.add(player2);

        SpringLayout.Constraints labelCons =
                layout.getConstraints(p1);
        labelCons.setX(Spring.constant(5));
        labelCons.setY(Spring.constant(10));

        //Adjust constraints for the text field so it's at
        //(<label's right edge> + 5, 5).
        SpringLayout.Constraints textFieldCons =
                layout.getConstraints(player1);
        textFieldCons.setX(Spring.sum(Spring.constant(5),
                labelCons.getConstraint(SpringLayout.EAST)));
        textFieldCons.setY(Spring.constant(10));

        SpringLayout.Constraints labelCon =
                layout.getConstraints(p2);
        labelCon.setX(Spring.constant(5));
        labelCon.setY(Spring.constant(50));

        //Adjust constraints for the text field so it's at
        //(<label's right edge> + 5, 5).
        SpringLayout.Constraints textFieldCon =
                layout.getConstraints(player2);
        textFieldCon.setX(Spring.sum(Spring.constant(5),
                labelCons.getConstraint(SpringLayout.EAST)));
        textFieldCon.setY(Spring.constant(50));


        JButton submit = new JButton("Start");
        content.add(submit);

        SpringLayout.Constraints buttonCon =
                layout.getConstraints(submit);
        buttonCon.setX(Spring.sum(Spring.constant(10),
                labelCons.getConstraint(SpringLayout.EAST)));
        buttonCon.setY(Spring.constant(90));

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                p1Name = player1.getText();
                p2Name = player2.getText();
                info.dispose();
                setupScoreBoard();
            }
        });

        info.setSize(250, 250);
        info.setLocation(560, 0);
        info.setVisible(true);
    }

    private void setupScoreBoard() {
        scoreBoard = new JFrame("Score Board");
        scoreBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel p1 = new JLabel("Player 1: " + p1Name);
        JLabel p2 = new JLabel("Player 2: " + p2Name);
        JLabel p1s = new JLabel("" + p1Score);
        JLabel p2s = new JLabel("" + p2Score);

        Container content = scoreBoard.getContentPane();
        SpringLayout layout = new SpringLayout();
        content.setLayout(layout);

        content.add(p1);
        content.add(p2);
        content.add(p1s);
        content.add(p2s);

        SpringLayout.Constraints labelCons =
                layout.getConstraints(p1);
        labelCons.setX(Spring.constant(5));
        labelCons.setY(Spring.constant(25));

        SpringLayout.Constraints labelCon =
                layout.getConstraints(p2);
        labelCon.setX(Spring.constant(5));
        labelCon.setY(Spring.constant(75));

        SpringLayout.Constraints scoreCons =
                layout.getConstraints(p1s);
        scoreCons.setX(Spring.constant(50));
        scoreCons.setY(Spring.constant(50));

        SpringLayout.Constraints scoreCon =
                layout.getConstraints(p2s);
        scoreCon.setX(Spring.constant(50));
        scoreCon.setY(Spring.constant(100));

        scoreBoard.setSize(250, 250);
        scoreBoard.setLocation(560, 0);
        scoreBoard.setVisible(true);
    }

    public void updateScore(int turn) {
        if (turn % 2 == 0)
            p2Score++;
        else
            p1Score++;
        setupScoreBoard();
    }

    public void initializeBoard() {
        pieces = new Images();//initialize images
        pause = false;
    }

    private void setupFrame() {
        frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setSize(550, 550);
        frame.setJMenuBar(controller.getMenu());
        frame.addMouseListener(controller.getMouse());
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        //displays Chess board
        boardBackground(g);
        highlightMoves(g);
        displayPieces(g);
        GameOver(g);
        repaint();
    }

    private void boardBackground(Graphics g) {
        // 8X8 Board
        xDimensions = (getWidth() / controller.getW());
        yDimensions = (getHeight() / controller.getH());

        int count = 0;
        //Dark brown and light brown Background squares
        for (int i = 0; i < controller.getW(); i++) {
            for (int j = 0; j < controller.getH(); j++) {
                if (count % 2 == 0) {
                    g.setColor(new Color(255, 204, 153));
                    g.fillRect(i * xDimensions, j * yDimensions, xDimensions, yDimensions);
                } else {
                    g.setColor(new Color(153, 76, 0));
                    g.fillRect(i * xDimensions, j * yDimensions, xDimensions, yDimensions);
                }
                count++;
            }
            count++;
        }

        //Highlight the square clicked by user
        g.setColor(Color.yellow);
        g.drawRect(mouseX * xDimensions, mouseY * yDimensions, xDimensions, yDimensions);
    }

    private void displayPieces(Graphics g) {
        //Player1's pieces display
        getPieces(g, 1, 'P', 8);
        getPieces(g, 1, 'C', 2);
        getPieces(g, 1, 'B', 2);
        getPieces(g, 1, 'H', 2);
        getPieces(g, 1, 'K', 1);
        getPieces(g, 1, 'Q', 1);

        //Player2's pieces display
        getPieces(g, 2, 'P', 8);
        getPieces(g, 2, 'C', 2);
        getPieces(g, 2, 'B', 2);
        getPieces(g, 2, 'H', 2);
        getPieces(g, 2, 'K', 1);
        getPieces(g, 2, 'Q', 1);

        if (pause) {
            g.setColor(new Color(255, 55, 0));
            Font p3 = new Font("Algerian", Font.PLAIN, 100);
            g.setFont(p3);
            g.drawString("Paused", getWidth() / 3 - 100, getHeight() / 2);
        }
    }

    private void getPieces(Graphics g, int player, char name, int numElements) {
        Image img;
        switch (name) {
            case 'P':
                if (player == 1) {
                    img = pieces.whitePawnImage;
                } else {
                    img = pieces.blackPawnImage;
                }
                break;
            case 'C':
                if (player == 1)
                    img = pieces.whiteCastleImage;
                else
                    img = pieces.blackCastleImage;
                break;
            case 'H':
                if (player == 1)
                    img = pieces.whiteHorseImage;
                else
                    img = pieces.blackHorseImage;
                break;
            case 'B':
                if (player == 1)
                    img = pieces.whiteBishopImage;
                else
                    img = pieces.blackBishopImage;
                break;
            case 'Q':
                if (player == 1)
                    img = pieces.whiteQueenImage;
                else
                    img = pieces.blackQueenImage;
                break;
            case 'K':
                if (player == 1)
                    img = pieces.whiteKingImage;
                else
                    img = pieces.blackKingImage;
                break;
            default:
                img = pieces.whitePawnImage;
                System.out.println("Invalid Image Selection");
                break;
        }//end switch case

        //player1 (white pieces)
        for (int index = 0; index < numElements; index++) {
            Piece p = controller.getPieces(player, name, index);

            //Only display pieces that are still valid. (alive)
            if (p != null && !p.isEaten)
                g.drawImage(img, xDimensions * p.positions.getX() + 7, yDimensions * p.positions.getY(), null);
        }
    }

    public void addMoves(Vector<Pair> moves) {
        this.moves = moves;
    }

    public void highlightMoves(Graphics g) {
        if (moves != null) {
            for (int i = 0; i < moves.size(); i++) {
                Pair p = moves.elementAt(i);
                //Highlight the square clicked by user
                g.setColor(Color.yellow);
                g.fillRect(p.getX() * xDimensions, p.getY() * yDimensions, xDimensions, yDimensions);
            }
        }
    }

    public void GameOver(Graphics g) {
        int over = controller.gameOver();

        if (over == 1) {
            g.setColor(Color.GREEN);
            Font p3 = new Font("Algerian", Font.PLAIN, 50);
            g.setFont(p3);
            g.drawString("Player 2 Wins!", getWidth() / 3 - 100, getHeight() / 3);
            pause = true;
        } else if (over == 2) {
            g.setColor(Color.GREEN);
            Font p3 = new Font("Algerian", Font.PLAIN, 50);
            g.setFont(p3);
            g.drawString("Player 1 Wins!", getWidth() / 3 - 100, getHeight() / 3);

            pause = true;
        }
    }
}
