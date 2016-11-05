/**
 * Created by ben on 2016-11-05.
 */
public class QContainer {
    String path;
    Node node;

    public QContainer(Node node){
        this.node = node;
        path = Integer.toString(node.getId());
    }

    public QContainer(Node node, String existingPath){
        this.node = node;
        this.path = existingPath + "," + Integer.toString(node.getId());
    }

    @Override
    public boolean equals (Object o){
        if(o instanceof QContainer){
            QContainer c = (QContainer) o;
            return node == c.node;
        }
        return false;
    }
}
