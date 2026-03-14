import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Treasure {

static JButton[][] cell;
static char[][] grid;
static int r,c,sr=-1,sc=-1,tr=-1,tc=-1;

public static void main(String[] args){

r=Integer.parseInt(JOptionPane.showInputDialog("Rows"));
c=Integer.parseInt(JOptionPane.showInputDialog("Cols"));

cell=new JButton[r][c];
grid=new char[r][c];

JFrame f=new JFrame("Treasure Hunt");
f.setLayout(new BorderLayout());
f.setSize(500,500);

JPanel p=new JPanel(new GridLayout(r,c));

for(int i=0;i<r;i++)
for(int j=0;j<c;j++){

cell[i][j]=new JButton("0");
grid[i][j]='0';

int x=i,y=j;

cell[i][j].addActionListener(e->{

String s=JOptionPane.showInputDialog("S,T,X,0");
if(s==null)return;

char v=s.toUpperCase().charAt(0);
grid[x][y]=v;
cell[x][y].setText(""+v);

if(v=='S'){sr=x;sc=y;cell[x][y].setBackground(Color.BLUE);}
if(v=='T'){tr=x;tc=y;cell[x][y].setBackground(Color.YELLOW);}
if(v=='X'){cell[x][y].setBackground(Color.RED);}
if(v=='0'){cell[x][y].setBackground(null);}
});

p.add(cell[i][j]);
}

JButton start=new JButton("Start BFS");
JButton reset=new JButton("Reset");

start.addActionListener(e->{if(sr!=-1&&tr!=-1)bfs();});

reset.addActionListener(e->{
sr=sc=tr=tc=-1;
for(int i=0;i<r;i++)
for(int j=0;j<c;j++){
grid[i][j]='0';
cell[i][j].setText("0");
cell[i][j].setBackground(null);
}
});

JPanel bottom=new JPanel();
bottom.add(start);
bottom.add(reset);

f.add(p,BorderLayout.CENTER);
f.add(bottom,BorderLayout.SOUTH);

f.setVisible(true);
}

static void bfs(){

Queue<int[]> q=new LinkedList<>();
boolean[][] vis=new boolean[r][c];

int[][] pr=new int[r][c];
int[][] pc=new int[r][c];

int[] dr={1,-1,0,0};
int[] dc={0,0,1,-1};

q.add(new int[]{sr,sc});
vis[sr][sc]=true;

while(!q.isEmpty()){

int[] cur=q.poll();
int x=cur[0],y=cur[1];

if(x==tr&&y==tc){
showPath(pr,pc);
return;
}

for(int i=0;i<4;i++){

int nr=x+dr[i],nc=y+dc[i];

if(nr>=0&&nc>=0&&nr<r&&nc<c&&!vis[nr][nc]&&grid[nr][nc]!='X'){
vis[nr][nc]=true;
pr[nr][nc]=x;
pc[nr][nc]=y;
q.add(new int[]{nr,nc});
}
}
}
JOptionPane.showMessageDialog(null,"No Path");
}

static void showPath(int[][] pr,int[][] pc){

int x=tr,y=tc;
int steps=0;

String path="Path (row,col):\n";

while(!(x==sr&&y==sc)){

path="("+x+","+y+") -> "+path;

if(grid[x][y]!='T')
cell[x][y].setBackground(Color.GREEN);

int px=pr[x][y];
int py=pc[x][y];

x=px;
y=py;
steps++;
}

path="("+sr+","+sc+") -> "+path;

JOptionPane.showMessageDialog(null,
"Optimal Path Found\nSteps: "+steps+"\n"+path);
}
}