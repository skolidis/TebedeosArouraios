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
        Rtree R= new Rtree();
        R.bulkInsert(nodes);
        R.printTree();
    }
}
