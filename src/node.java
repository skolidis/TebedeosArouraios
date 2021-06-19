public class node implements Comparable<node> {
    float[] coordinates;

    public node(float[] coordinates){
        this.coordinates=coordinates;
    }

    public node(){}

    public float getX(){
        return coordinates[0];
    }

    @Override
    public int compareTo(node n) {
        return (int)(this.coordinates[0] - n.coordinates[0]);
    }
}
