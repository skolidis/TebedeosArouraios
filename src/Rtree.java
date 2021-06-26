import java.util.ArrayList;
import java.util.Arrays;

public class Rtree {
    ArrayList<Rea> root;
    int max;
    int min;
    ArrayList<Float> boundlow;
    ArrayList<Float> boundhigh;
    int dims;

    public Rtree(int dims,ArrayList<Float> boundlow,ArrayList<Float> boundhigh){
        max= (int)Math.floor(32000.0/(4*dims));
        min= max/2;
        root=new ArrayList<Rea>();
        this.boundlow=boundlow;
        this.boundhigh=boundhigh;
        this.dims=dims;
    }

    public void insert(node n,boolean firstTime){
        if(root.size()==0)
            root.add(new Rea(max,n.getDims(),0,null));

        Rea currentR=chooseSubtree(root,n);
        while(!currentR.isLeaf()){
            currentR=chooseSubtree(currentR.getNodes(),n);
        }
        if(!currentR.isFullofLeaves()) {
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
            split(currentR);
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

    private void split(Rea currentRea){
        int axis=chooseSplitAxis(currentRea);
        int index=chooseSplitIndex(currentRea,axis);
        if(currentRea.getParent()==null) {
            if(root.size()<max){
                Rea r= new Rea(max,dims,0,null);
                node[] ns= currentRea.deleteAndReturn(index);
                for (node n : ns)
                    r.insertNode(n);
                root.add(root.indexOf(currentRea)+1,r);
                r.adjustBounds();
            }
            else{
                currentRea.splitAndDivide(index);
            }
        }
        else{
            if(!currentRea.getParent().isFullofNodes()){
                Rea r= new Rea(max,dims,0,null);
                node[] ns= currentRea.deleteAndReturn(index);
                for (node n : ns)
                    r.insertNode(n);
                currentRea.getParent().addRea(r,currentRea);
                r.adjustBounds();
            }
            else
                currentRea.splitAndDivide(index);
        }
    }

    private int chooseSplitAxis(Rea currentRea){
        return currentRea.lowestAxis();
    }

    private int chooseSplitIndex(Rea currentRea, int axis){
        return max/2;
    }

    public void bulkInsert(node[] nodes){
        Arrays.sort(nodes);
        for(int i=0;i<nodes.length;i++)
        {
            if (i%max==0) {
                root.add(new Rea(max,nodes[0].getDims(),0,null));
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
