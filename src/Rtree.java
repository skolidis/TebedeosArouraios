import java.util.Arrays;

public class Rtree {
    Rea[] root;
    int max;
    int min;
    int rootCapacity;
    float[] boundlow;
    float[] boundhigh;

    public Rtree(int dims,float[] boundlow,float[] boundhigh){
        max= (int)Math.floor(32000.0/(4*dims));
        min= max/2;
        root=new Rea[max];
        rootCapacity=0;
        this.boundlow=boundlow;
        this.boundhigh=boundhigh;
    }

    public void insert(node n){
        if(rootCapacity==0)
            root[rootCapacity++]=new Rea(max,n.getDims());

        Rea currentR=chooseSubtree(root,n,rootCapacity);
        while(!currentR.isLeaf()){
            currentR=chooseSubtree(currentR.getNodes(),n,currentR.getNodes().length);
        }
        if(!currentR.isFull()) {
            currentR.insertNode(n);
        }
    }

    public Rea chooseSubtree(Rea[] rea, node n,int numberOfNodes){
        float[] distances=new float[n.getDims()];
        float[] coord= n.getCoordinates();
        float sum;

        for (int i=0;i<numberOfNodes;i++){
            System.out.print(i);
            float[] center=rea[i].getCenter();
            for(int j=0;j<center.length;j++){
                System.out.print(center[j]+"\n");
            }
            sum=0;
            for(int j=0;j<center.length;j++)
                sum+=Math.pow(coord[j]-center[j],2);
            distances[i]= (float) Math.sqrt(sum);
        }

        float min=distances[0];
        int pos=0;

        for(int i=0;i<numberOfNodes;i++){
            if(distances[i]<min){
                min=distances[i];
                pos=i;
            }
        }

        return rea[pos];
    }

    public void bulkInsert(node[] nodes){
        Arrays.sort(nodes);
        rootCapacity=-1;
        for(int i=0;i<nodes.length;i++)
        {
            if (i%max==0) {
                rootCapacity++;
                root[rootCapacity]=new Rea(max,nodes[0].getDims());
            }
            root[rootCapacity].bulkAddNode(nodes[i]);
        }
    }

    public void printTree(){
        for(int i=0;i<rootCapacity;i++){
            root[i].printRea();
        }
    }
}
