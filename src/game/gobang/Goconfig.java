package game.gobang;
import java.util.HashMap;

public interface Goconfig {
    public static final int row=15;  //棋盘行数
    public static final int column=15; //棋盘列数
    public static final int square_size=35;  //棋盘方格大小
    public static final int radius=12;  //棋子半径
    public static final int X=30,Y=30;   //棋盘左上角顶点坐标
    public static Piece go[][]=new Piece[15][15];  //记录落子的数组
    public static int weightarr[][]=new int[15][15];  //记录每个位置权重的数组
    public static HashMap<String,Integer> map = new HashMap<String,Integer>();   //权值设置

}
