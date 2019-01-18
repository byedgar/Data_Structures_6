import java.lang.reflect.Array;
import java.util.concurrent.ThreadLocalRandom;
public class Main {

    public static void main(String[] args) {
        final int MIN = -100;
        final int MAX = 100;
        int treeSize = 1;
        int randomNumber;
//
//        Tree[] tree = new Tree[treeSize];
//        for (int i = 0; i < treeSize; i++) {
//            tree[i] = new TreeImpl();
//        }
//
//        for (int i = 0; i < treeSize; i++) {
//            for (int j = 0; j < 20; j++) {
//                randomNumber = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
//                tree[i].add(1);
//            }
//            tree[i].displayLevel();
//            tree[i].displayTree();
//        }



        Tree trees = new TreeImpl();
        trees.add(20);
        trees.add(21);
        trees.add(19);
        trees.add(21);
        trees.add(18);
        trees.add(17);



        trees.displayTree();
        trees.displayLevel();

    }

}