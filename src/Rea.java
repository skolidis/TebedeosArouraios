import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Rea {
    float[] boundlow;
    float[] boundhigh;
    int level;
    ArrayList<Rea> nodes;
    ArrayList<node> leaves;
    int nodeLimit;
    Rea parent;

    public Rea(int nodeLimit,int dimNumber,int level, Rea parent){
        nodes=new ArrayList<Rea>();
        leaves=new ArrayList<node>();
        this.nodeLimit=nodeLimit;
        boundhigh=new float[dimNumber];
        boundlow=new float[dimNumber];
        this.parent=parent;
        this.level=level;
    }

    public void insertNode(node n){
        if(leaves.size()<nodeLimit) {
            if(leaves.size()==0){
                boundhigh=n.getCoordinates();
                boundlow=n.getCoordinates();
            }
            leaves.add(n);
            Collections.sort(leaves);
        }
    }

    public node[] getMaxDistances(node n){
        float[] center=getCenter();
        node c=new node(center);
        double[] distances= new double[leaves.size()+1];
        int[] ids=new int[leaves.size()+1];
        double temp1;
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

    public double minDist(node Point){
        float min=0;
        float rd;
        int n=Point.getDims();

        for (int i=0; i<n;i++){
            if (boundlow[i]>n){
                rd=boundlow[i];
            }else if (boundhigh[i]<n){
                rd=boundhigh[i];
            }else{
                rd=n;
            }
            min+=Math.pow(n-rd,2);
        }
        return sqrt(min);
    }

    public ArrayList<Rea> minDistRea(node Point){
        float min=0;
        ArrayList<Rea> near=nodes;
        int n=Point.getDims(),i=0;
        float[] coord= Point.getCoordinates();
        float minLow=boundlow[i],minHigh=boundhigh[i];

        for (i=0; i<n;i++){
            if (minLow<boundlow[i] && boundhigh[i]>minHigh){
                minLow=boundlow[i];
                minHigh=boundhigh[i];
                near=nodes;
            }

        }
        return near;
    }

    private void removeLeaves(int[] ids){
        for (int id : ids)
            leaves.remove(id);
    }

    public void adjustBounds(){
        if(leaves.size()>0) {
            float[] coordinates = leaves.get(0).getCoordinates();
            boundlow=coordinates;
            boundhigh=coordinates;
            for (int i = 1; i < leaves.get(0).getDims(); i++) {
                coordinates=leaves.get(i).getCoordinates();
                if (coordinates[i] < boundlow[i])
                    boundlow[i] = coordinates[i];
                if (coordinates[i] > boundhigh[i])
                    boundhigh[i] = coordinates[i];
            }
        }
        else{
            float[] coordinates1,coordinates2;
            for(int i=0;i<nodes.size();i++){
                coordinates1=nodes.get(i).getBoundhigh();
                coordinates2=nodes.get(i).getBoundlow();
                for(int j=0;j<coordinates1.length;j++){
                    if (coordinates1[j] > boundhigh[j])
                        boundhigh[j] = coordinates1[j];
                    if (coordinates2[j] < boundlow[j])
                        boundlow[i] = coordinates2[j];
                }
            }
        }
        if(parent!=null)
            parent.adjustBounds();
    }

    public node[] deleteAndReturn(int index){
        node[] ns=new node[nodeLimit-index-1];
        for(int i=index;i<nodeLimit;i++)
            ns[i]= leaves.get(i);
        if (nodeLimit > index) {
            leaves.subList(index, nodeLimit).clear();
        }
        adjustBounds();
        return ns;
    }

    public void splitAndDivide(int index){
        Rea r1=new Rea(nodeLimit,boundhigh.length,level+1,this);
        Rea r2=new Rea(nodeLimit,boundhigh.length,level+1,this);
        for (int i=0;i<=index;i++){
            r1.insertNode(leaves.get(i));
        }
        for (int i=index+1;i<nodeLimit;i++){
            r2.insertNode(leaves.get(i));
        }
        nodes.add(r1);
        r1.adjustBounds();
        nodes.add(r2);
        r2.adjustBounds();
        leaves.clear();
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

    public int lowestAxis(){
        float[] sums=new float[boundlow.length];
        for(int i=0;i<leaves.size();i++){
            float[] coord=leaves.get(i).getCoordinates();
            for(int j=0;j<coord.length;j++){
                sums[j]+=coord[j];
            }
        }
        int pos=0;
        float min=sums[0];
        for (int i=0;i<sums.length;i++){
            if(sums[i]<min){
                min=sums[i];
                pos=i;
            }
        }
        return pos;
    }

    public void addRea(Rea r,Rea nextTo){nodes.add(nodes.indexOf(nextTo),r);}

    public boolean isLeaf(){ return nodes.size()==0; }

    public boolean isFullofLeaves(){ return leaves.size()==nodeLimit;}

    public boolean isFullofNodes(){ return nodes.size()==nodeLimit;}

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

    public Rea getParent(){ return parent;}

    public float[] getBoundlow(){return boundlow;}

    public float[] getBoundhigh(){return boundhigh;}

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
