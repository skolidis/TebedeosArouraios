import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        float[] y={1,2,3};
        float[] z={2,2,3};
        float[] x={7,2,3};
        node a=new node(x);
        node b=new node(y);
        node c=new node(z);
        node[] nodes={a,b,c};
        float[] boundlow={0,0,0};
        float[] boundhigh={10,10,10};
        Rtree R=new Rtree(3,boundlow,boundhigh);
        R.insert(a);
        R.insert(b);
        R.insert(c);
        R.printTree();
    }
}
