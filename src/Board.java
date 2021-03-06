import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

public class Board extends JComponent implements MouseInputListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    private Point[][] points;
    private int size = 25;
    public int editType = 0;

    public Board(int length, int height) {
        addMouseListener(this);
        addComponentListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        setOpaque(true);
    }

    private void initialize(int length, int height) {
        points = new Point[length][height];

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y] = new Point();
            }
        }
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                if (x == points.length-1) {
                    points[x][y].next = points[0][y];
                    points[x][y].prev = points[x - 1][y];
                }
                else if (x==0) {
                    points[x][y].next = points[x + 1][y];
                    points[x][y].prev = points[points.length - 1][y];
                }
                else {
                    points[x][y].next = points[x + 1][y];
                    points[x][y].prev = points[x - 1][y];
                }
                if (y<2  || y>3){
                    points[x][y].setType(5);
                }
                if (y==2){
                    points[x][y].leftBelt=true;
                    points[x][y].rightBeltPoint=points[x][3];
                }
                if (y==3){
                    points[x][y].rightBelt=true;
                    points[x][y].leftBeltPoint=points[x][2];
                }
            }
        }
    }

    public void iteration() {
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].moved = false;
            }
        }

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].move();
            }
        }
        this.repaint();
    }

    public void clear() {
        /*for (int x = 0; x < points.length; ++x)
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].clear();
            }*/
        int dlugosc = (this.getWidth() / size) + 1;
        int wysokosc = 6; //= (this.getHeight() / size) + 1;
        initialize(dlugosc, wysokosc);
        this.repaint();
    }


    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        g.setColor(Color.GRAY);
        drawNetting(g, size);
    }

    private void drawNetting(Graphics g, int gridSpace) {
        Insets insets = getInsets();
        int firstX = insets.left;
        int firstY = insets.top;
        int lastX = this.getWidth() - insets.right;
        int lastY = this.getHeight() - insets.bottom;

        int x = firstX;
        while (x < lastX) {
            g.drawLine(x, firstY, x, lastY);
            x += gridSpace;
        }

        int y = firstY;
        while (y < lastY) {
            g.drawLine(firstX, y, lastX, y);
            y += gridSpace;
        }

        for (x = 0; x < points.length; ++x) {
            for (y = 0; y < points[x].length; ++y) {
                float a = 1.0F;
                if (points[x][y].getType()==0)
                    g.setColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
                if (points[x][y].getType()==1)
                    g.setColor(new Color(1.0f, 0.5f, 0.0f, 1.0f));
                if (points[x][y].getType()==2)
                    g.setColor(new Color(0.0f, 0.0f, 1.0f, 1.0f));
                if (points[x][y].getType()==3)
                    g.setColor(new Color(1.0f, 0.0f, 0.0f, 1.0f));
                if (points[x][y].getType()==5)
                    g.setColor(new Color(0.0f, 1.0f, 0.0f, 1.0f));

                g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            if(editType==0){
                points[x][y].clicked();
            }
            else {
                points[x][y].setType(editType);
            }
            this.repaint();
        }
    }

    public void componentResized(ComponentEvent e) {
        int dlugosc = (this.getWidth() / size) + 1;
        int wysokosc = 6; //= (this.getHeight() / size) + 1;
        initialize(dlugosc, wysokosc);
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            if(editType==0){
                points[x][y].clicked();
            }
            else {
                points[x][y].setType(editType);
            }
            this.repaint();
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

}
