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

    public AlgoVisualizer(int N, int M){
        data = new MazeData(N,M);
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
        setData(-1,-1,false);

        renderRandom(data.getEntranceX(),data.getEntranceY() + 1);

//        if (!go(data.getEntranceX(),data.getEntranceY()))
//            System.out.println("The maze has no solution!");

        setData(-1,-1,false);
    }

    private void renderRandom(int x, int y){
        if (!data.inArea(x,y)){
            throw new IllegalArgumentException("Render random maze failed,because (x,y) is not in area.");
        }

        //访问到当前位置
        data.visited[x][y] = true;
//        data.maze[x][y] = MazeData.ROAD;
        setDataForRender(x,y);

        //若遍历到出口前一个方格，结束
//        if (x == data.getExitX() && y == data.getExitY() - 1)
//            return;

        for (int i = 0; i < 4; i++){
            int newX = x + d[i][0] * 2;
            int newY = y + d[i][1] * 2;

            if (data.inArea(newX,newY) && !data.visited[newX][newY]){
                //打破墙
                setDataForRender(x + d[i][0],y + d[i][1]);
                renderRandom(newX,newY);
            }

        }

//        setDataForRender(x,y);
    }

    private boolean go(int x, int y) {
        if (!data.inArea(x,y)){
            throw new IllegalArgumentException("x,y are out of index in go function");
        }

        //访问到当前位置
        data.visited[x][y] = true;

        setData(x,y,true);

        if (x == data.getExitX() && y == data.getExitY())
            return true;

        for (int i = 0; i < 4; i++){
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            if (data.inArea(newX,newY) &&
                    data.getMaze(newX,newY) == MazeData.ROAD &&
                    !data.visited[newX][newY]){
                if (go(newX,newY)){
                    return true;
                }
            }
        }
        data.path[x][y] = false;
        setData(x,y,false);
        return false;
    }

    private void setDataForRender(int x,int y) {
        if (data.inArea(x, y)){
            data.maze[x][y] = MazeData.ROAD;
        }
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private void setData(int x,int y,boolean isPath) {
        if (data.inArea(x, y)){
            data.path[x][y] = isPath;
        }
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    // TODO: 根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{}
    private class AlgoMouseListener extends MouseAdapter{}

    public static void main(String[] args) {

        String mazeFile = "maze_101_101.txt";

        AlgoVisualizer vis = new AlgoVisualizer(41,41);
    }

}