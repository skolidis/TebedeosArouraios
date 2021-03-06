public class node implements Comparable<node> {
    float[] coordinates;

    public node(float[] coordinates){
        this.coordinates=coordinates;
    }

    public node(){}

    public float getX(){
        return coordinates[0];
    }

    public int getDims(){
        return coordinates.length;
    }

    public float[] getCoordinates(){
        float[] coordinates=new float[getDims()];
        for(int i=0;i<getDims();i++)
            coordinates[i]=this.coordinates[i];
        return coordinates;
    }

    public double calculateDistance(node n){
        float sum=0;
        float[] other=n.getCoordinates();
        for(int i=0;i<coordinates.length;i++)
            sum+=Math.pow(coordinates[i] -other[i],2);
        return (float) Math.sqrt(sum);
    }

    public void printNode(){
        for(int i=0;i<coordinates.length;i++)
            System.out.print(coordinates[i]+" ");
        System.out.print("\n");
    }

    @Override
    public int compareTo(node n) {
        return (int)(this.coordinates[0] - n.coordinates[0]);
    }
}
