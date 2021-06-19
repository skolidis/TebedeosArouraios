public class Rtree {
    Rea[] root;

    public void bulkInsert(node[] nodes){
        nodes=sort(nodes);

    }

    private node[] sort(node[] nodes){
        node temp=new node();
        for(int i=0;i<nodes.length;i++)
            for(int j=0;j<nodes.length;j++){
                if (nodes[i].getX()>nodes[j].getX())
                    temp=nodes[i];
                    nodes[i]=nodes[j];
                    nodes[j]=temp;
            }
        return nodes;
    }
}
