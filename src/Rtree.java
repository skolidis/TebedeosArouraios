import java.util.ArrayList;
import java.util.Arrays;

public class Rtree {
    ArrayList<Rea> root;
    int max;
    int min;
    ArrayList<Float> boundlow;
    ArrayList<Float> boundhigh;

    public Rtree(int dims,ArrayList<Float> boundlow,ArrayList<Float> boundhigh){
        max= (int)Math.floor(32000.0/(4*dims));
        min= max/2;
        root=new ArrayList<Rea>();
        this.boundlow=boundlow;
        this.boundhigh=boundhigh;
    }

    public void insert(node n,boolean firstTime){
        if(root.size()==0)
            root.add(new Rea(max,n.getDims(),0));

        Rea currentR=chooseSubtree(root,n);
        while(!currentR.isLeaf()){
            currentR=chooseSubtree(currentR.getNodes(),n);
        }
        if(!currentR.isFull()) {
            currentR.insertNode(n);
        }
        else{
            OverflowTreatment(currentR,firstTime,n);
        }
    }

    private void OverflowTreatment(Rea currentR,boolean firstTime,node n){
        if(currentR.getLevel()!=0&&firstTime){
            firstTime=false;
            reInsert(currentR,n,firstTime);
        }
        else{

        }
    }

    private Rea chooseSubtree(ArrayList<Rea> rea, node n){
        float[] distances=new float[n.getDims()];
        float[] coord= n.getCoordinates();
        float sum;

        for (int i=0;i<rea.size();i++){
            System.out.print(i);
            float[] center=rea.get(i).getCenter();
            sum=0;
            for(int j=0;j<center.length;j++)
                sum+=Math.pow(coord[j]-center[j],2);
            distances[i]= (float) Math.sqrt(sum);
        }

        float min=distances[0];
        int pos=0;

        for(int i=0;i<rea.size();i++){
            if(distances[i]<min){
                min=distances[i];
                pos=i;
            }
        }

        return rea.get(pos);
    }

    private void reInsert(Rea currentR,node n,boolean firstTime){
        node[] maxdistances=currentR.getMaxDistances(n);
        for (node maxdistance : maxdistances)
            insert(maxdistance,firstTime);

    }

    public void bulkInsert(node[] nodes){
        Arrays.sort(nodes);
        for(int i=0;i<nodes.length;i++)
        {
            if (i%max==0) {
                root.add(new Rea(max,nodes[0].getDims(),0));
            }
            root.get(i).bulkAddNode(nodes[i]);
        }
    }

    public void printTree(){
        for(int i=0;i<root.size();i++){
            root.get(i).printRea();
        }
    }
}
