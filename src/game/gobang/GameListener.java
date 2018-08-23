package game.gobang;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;


public class GameListener extends MouseAdapter implements ActionListener,Goconfig{
    private GameMain main;
    private Graphics2D g;
    private boolean flag=true;
    private String type="人人对战";
    private JComboBox cbItem;
    private ArrayList<Chess> list = new ArrayList<Chess>();
    public GameListener(GameMain main,JComboBox cbItem) {
        this.main=main;
        this.cbItem=cbItem;
    }

    public void mouseClicked(MouseEvent e) {
        if(g==null) {
            g = (Graphics2D) main.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
        int x=e.getX();
        int y=e.getY();

        if(x<30 || y<30 || x>520 || y>520)
            System.out.println("请在棋盘内点击");
        else {
            int cx=x-X-((x-X)/square_size)*square_size;int cy=y-Y-((y-Y)/square_size)*square_size;
            if(cx>17)
                x=X+((x-X)/square_size+1)*square_size;
            else
                x=X+((x-X)/square_size)*square_size;
            if(cy>17)
                y=Y+((y-Y)/square_size+1)*square_size;
            else
                y=Y+((y-Y)/square_size)*square_size;
            int zx=(x-X)/square_size;
            int zy=(y-Y)/square_size;
            Chess tmpchess=new Chess(zx,zy);
            if(go[zx][zy].color==0) {
                draw(tmpchess);
                if(type.equals("人机对战")) {
                    Piece.weightReset();
                    tmpchess=Piece.weight();
                    draw(tmpchess);  //画出该子，并判断输赢；
                }
            }
            else
                System.out.println("该位置不能落子");
        }
    }


    public void draw(Chess tmpchess) {
        int zx=tmpchess.x;int zy=tmpchess.y;
        int x=zx*35+30;int y=zy*35+30;
        if(flag) {
            g.setColor(Color.black);
        }
        else {
            g.setColor(Color.WHITE);
        }
        g.fillOval(x-radius, y-radius, radius*2, radius*2);
        Color col=g.getColor();
        if(col==Color.black)
            go[zx][zy].color=1;
        else if(col==Color.WHITE)
            go[zx][zy].color=2;
        if(go[zx][zy].judge(zx, zy)!=0) {
            if (flag) {
                JOptionPane.showMessageDialog(main, "黑棋胜利！");
            } else {
                JOptionPane.showMessageDialog(main, "白棋胜利！");
            }
            main.removeMouseListener(this);
            cbItem.setEnabled(true);        //设置可以操作
            main.setButton(false);
//            JOptionPane.showMessageDialog(main, "游戏结束");
        }
        flag=!flag;
        list.add(tmpchess);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("开始游戏")) {
            for(int i=0;i<row;i++) {
                for(int j=0;j<column;j++) {
                    go[i][j].color=0;
                }
            }
            Piece.weightReset();           //开始前重置棋盘的权重
            flag=true;                     //第一个棋子被置为黑色
            cbItem.setEnabled(false);
            list.clear();                 //清空棋子数组
            main.repaint();
            main.removeMouseListener(this);  //点击开始才能进行游戏
            main.addMouseListener(this);
            main.setButton(true);              //设置悔棋、认输可点
        }else if(e.getActionCommand().equals("悔棋")){
            if(list.size()>=1) {
                Chess chess=list.remove(list.size()-1);
                go[chess.x][chess.y].color=0;
                flag=!flag;
                main.repaint();
            }
        }else if(e.getActionCommand().equals("认输")) {
            if (flag) {
                JOptionPane.showMessageDialog(main, "黑棋认输，白棋胜利！");
            } else {
                JOptionPane.showMessageDialog(main, "白棋认输，黑棋胜利！");
            }
            main.removeMouseListener(this);   // 移除棋盘面板上的鼠标动作监听方法和事件处理类对象。
            cbItem.setEnabled(true);        //设置可以操作
            main.setButton(false);
        }else if(e.getSource() instanceof JComboBox){
            JComboBox<String> cbItem = (JComboBox<String>) e.getSource();// 获取事件源对象
            type = cbItem.getSelectedItem().toString();      // 获取选择的对战模式
        }
    }
}

