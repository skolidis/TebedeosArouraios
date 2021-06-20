import java.util.Arrays;

public class Rtree {
    Rea[] root;
    int max;
    int min;

    public void bulkInsert(node[] nodes){
        Arrays.sort(nodes);
        for (int i=0;i<nodes.length;i++)
        max= (int) Math.ceil(Math.sqrt(nodes.length));
        min= max/2;
        root=new Rea[max];
        int j=-1;
        for(int i=0;i<nodes.length;i++)
        {
            if (i%max==0) {
                j++;
                root[j]=new Rea(max);
            }
            root[j].bulkAddNode(nodes[i]);
        }
    }

    public void printTree(){
        for(int i=0;i< root.length;i++){
            root[i].printRea();
        }
    }
}
