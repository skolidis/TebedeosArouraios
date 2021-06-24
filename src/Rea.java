import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Rea {
    float[] boundlow;
    float[] boundhigh;
    int level;
    ArrayList<Rea> nodes;
    ArrayList<node> leaves;
    int nodeLimit;

    public Rea(int nodeLimit,int dimNumber,int level){
        nodes=new ArrayList<Rea>();
        leaves=new ArrayList<node>();
        this.nodeLimit=nodeLimit;
        boundhigh=new float[dimNumber];
        boundlow=new float[dimNumber];
        this.level=level;
    }

    public void insertNode(node n){
        if(leaves.size()<nodeLimit) {
            leaves.add(n);
            Collections.sort(leaves);
        }
    }

    public node[] getMaxDistances(node n){
        float[] center=getCenter();
        node c=new node(center);
        float[] distances= new float[leaves.size()+1];
        int[] ids=new int[leaves.size()+1];
        float temp1;
        int temp2;
        for(int i=0;i<leaves.size();i++){
            distances[i]=leaves.get(i).calculateDistance(c);
            ids[i]=i;
        }
        for(int i=0;i<distances.length;i++){
            for(int j=1;j<distances.length;j++){
                if(distances[i]<distances[j]){
                    temp1=distances[i];
                    distances[i]=distances[j];
                    distances[j]=temp1;
                    temp2=ids[i];
                    ids[i]=ids[j];
                    ids[j]=temp2;
                }
            }
        }

        node[] maxnodes=new node[nodeLimit/2];

        for(int i=0;i<nodeLimit/2;i++){
            maxnodes[i]=leaves.get(ids[i]);
        }

        removeLeaves(ids);
        adjustBounds();
        return maxnodes;
    }

    private void removeLeaves(int[] ids){
        for (int id : ids)
            leaves.remove(id);
    }

    private void adjustBounds(){
        float[] coordinates=leaves.get(0).getCoordinates();
        for(int i=0;i<leaves.get(0).getDims();i++){
            if (coordinates[i]<boundlow[i])
                boundlow[i]=coordinates[i];
            if (coordinates[i]>boundhigh[i])
                boundhigh[i]=coordinates[i];
        }
    }
    
    public void printRea(){
        System.out.print("R \n");
        if(nodes.size()>0){
            for (Rea node : nodes) node.printRea();
        }
        if(leaves.size()>0){
            for (node leaf : leaves) leaf.printNode();
        }
    }

    public boolean isLeaf(){ return nodes.size()==0; }

    public boolean isFull(){ return nodes.size()==nodeLimit;}

    public float[] getCenter(){
        float[] center=new float[boundlow.length];
        for (int i=0;i<boundlow.length;i++)
            center[i]=(boundlow[i]+boundhigh[i])/2;
        return center;
    }

    public ArrayList<Rea> getNodes(){
        return nodes;
    }

    public int getLevel(){return level;}


    public void bulkAddNode(node n){
        if (leaves.size()==0){
            leaves.add(n);
            boundlow=n.getCoordinates();
            boundhigh=n.getCoordinates();
        }
        else{
            leaves.add(n);
            float[] coordinates=n.getCoordinates();
            for(int i=0;i<n.getDims();i++){
                if (coordinates[i]<boundlow[i])
                    boundlow[i]=coordinates[i];
                if (coordinates[i]>boundhigh[i])
                    boundhigh[i]=coordinates[i];
            }

        }
    }
}
