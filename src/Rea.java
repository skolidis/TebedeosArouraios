import java.util.Arrays;

public class Rea {
    float[] boundlow;
    float[] boundhigh;
    Rea[] nodes;
    node[] leaves;
    int nodeLimit;
    int leavenumber;
    int nodenumber;

    public Rea(int nodeLimit,int dimNumber){
        nodes=new Rea[nodeLimit];
        leaves=new node[nodeLimit];
        this.nodeLimit=nodeLimit;
        leavenumber=0;
        nodenumber=0;
        boundhigh=new float[dimNumber];
        boundlow=new float[dimNumber];
    }

    public void insertNode(node n){
        leaves[leavenumber++]=n;
        Arrays.sort(leaves,0,leavenumber);
    }

    public void bulkAddNode(node n){
        if (leavenumber==0){
            leaves[leavenumber++]=n;
            boundlow=n.getCoordinates();
            boundhigh=n.getCoordinates();
        }
        else{
            leaves[leavenumber++]=n;
            float[] coordinates=n.getCoordinates();
            for(int i=0;i<n.getDims();i++){
                if (coordinates[i]<boundlow[i])
                    boundlow[i]=coordinates[i];
                if (coordinates[i]>boundhigh[i])
                    boundhigh[i]=coordinates[i];
            }

        }
    }
    
    public boolean isLeaf(){ return nodenumber==0; }
    
    public boolean isFull(){ return nodenumber==nodeLimit;}

    public float[] getCenter(){
        float[] center=new float[boundlow.length];
        for (int i=0;i<boundlow.length;i++)
            center[i]=(boundlow[i]+boundhigh[i])/2;
        return center;
    }
    
    public Rea[] getNodes(){
        return nodes;
    }
    
    public void printRea(){
        System.out.print("R \n");
        if(nodenumber>0){
            for (int i=0;i<nodenumber;i++)
                nodes[i].printRea();
        }
        if(leavenumber>0){
            for(int i=0;i<leavenumber;i++)
                leaves[i].printNode();
        }
    }
}
