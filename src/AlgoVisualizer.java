import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

/**
 * @Author: renxiaoya
 * @Date: 2018/6/4 19 30
 */
public class AlgoVisualizer {
    private static int DELAY = 20;
    //每一个小格子的像素数
    private static int blockSide = 8;

    // TODO: 创建自己的数据
    private MazeData data;  //数据
    private AlgoFrame frame;  //视图

    private static final int d[][] = {{-1,0},{0,1},{1,0},{0,-1}};

    public AlgoVisualizer(String mazeFile){

        // 初始化数据
        data = new MazeData(mazeFile);

        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Maze Solver Visualization",sceneWidth,sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });

    }

    //动画逻辑
    private void run(){
        // TODO: 编写自己的动画逻辑
        setData(-1,-1);

        go(data.getEntranceX(),data.getEntranceY());

        setData(-1,-1);
    }

    private void go(int x, int y) {
        if (!data.inArea(x,y)){
            throw new IllegalArgumentException("x,y are out of index in go function");
        }

        //访问到当前位置
        data.visited[x][y] = true;

        setData(x,y);

        if (x == data.getExitX() && y == data.getExitY())
            return;

        for (int i = 0; i < 4; i++){
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            if (data.inArea(newX,newY) &&
                    data.getMaze(newX,newY) == MazeData.ROAD &&
                    !data.visited[newX][newY]){
                go(newX,newY);
            }
        }
        return;
    }

    private void setData(int x,int y) {
        if (data.inArea(x, y)){
            data.path[x][y] = true;
        }
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // TODO: 根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{}
    private class AlgoMouseListener extends MouseAdapter{}

    public static void main(String[] args) {

        String mazeFile = "maze_101_101.txt";

        AlgoVisualizer vis = new AlgoVisualizer(mazeFile);
    }

}