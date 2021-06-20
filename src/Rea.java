public class Rea {
    float[] boundlow;
    float[] boundhigh;
    Rea[] connection;
    node[] leaves;
    int leavecapacity;
    int concapacity;

    public Rea(int conNumber){
        connection=new Rea[conNumber];
        leaves=new node[conNumber];
        leavecapacity=0;
        concapacity=0;
    }

    public void printRea(){
        System.out.print("R \n");
        if(concapacity>0){
            for (int i=0;i<concapacity;i++)
                connection[i].printRea();
        }
        if(leavecapacity>0){
            for(int i=0;i<leavecapacity;i++)
                leaves[i].printNode();
        }
    }

    public void bulkAddNode(node n){
        if (leavecapacity==0){
            leaves[leavecapacity++]=n;
            boundlow=n.getCoordinates();
            boundhigh=n.getCoordinates();
        }
        else{
            leaves[leavecapacity++]=n;
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
