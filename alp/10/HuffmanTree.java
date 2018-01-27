import java.util.PriorityQueue;
import java.util.Map;
import java.util.Hashtable;

public class HuffmanTree {
    protected class Node implements Comparable {
        public int count;
        public Node l = null;
        public Node r = null;
        public Character c = null;
        public boolean isLeaf;
        // public String code = "";
        public boolean[] code = {};

        public Node(Character c, int count) {
            this.c = c;
            this.count = count;
            isLeaf = true;
        }
        public Node(Node l, Node r, int count) {
            this.l = l;
            this.r = r;
            this.count = count;
            isLeaf = false;
        }
        public int compareTo(Object node) {
            int count2 = ((Node)node).count;
            int cmp = count > count2 ? +1 : (count < count2 ? -1 : 0);
            if (cmp != 0) { return cmp; }
            int height = height();
            int height2 = ((Node)node).height();
            return height > height2 ? +1 : (height < height2 ? -1 : 0);
        }
        public String toString() {
            if (isLeaf) {
                return ("L" + c + count);
            } else {
                return ("P(" + l + "," + r + ")");
            }
        }

        private void genCodes() {
            if (!isLeaf) {
                l.code = appendToBoolArray(code, false);
                r.code = appendToBoolArray(code, true);
                l.genCodes();
                r.genCodes();
            }
        }

        public void printLeaves() {
            if (isLeaf) {
                System.out.println(c + " - " + count + " : " + codeToString(code));
            } else {
                l.printLeaves();
                r.printLeaves();
            }
        }

        private int height() {
            if (isLeaf) {
                return 0;
            } else {
                int hl = l.height();
                int hr = r.height();
                return 1 + (hl>hr ? hl : hr);
            }
        }
    }

    protected Node root = null;
    protected Map<Character, boolean[]> codebook = null;

    public HuffmanTree(Map<Character, Integer> cc) {
        //build priority queue
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : cc.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        // reduce the tree till it has only one node left, this becomes
        // root node of the tree
        while (true) {
            Node node1 = pq.poll(); // smallest node
            Node node2 = pq.poll(); // second smallest node

            if (node2 == null) {
                // if node2 is null, there was only one node
                // left in the Queue -> this node becomes the
                // root node of the now fully built tree
                root = node1;
                break;
            }

            Node newNode = new Node(node1, node2, node1.count + node2.count);
            pq.add(newNode);
        }
        root.genCodes();
        codebook = new Hashtable<>();
        genCodebook(root);
    }

    public void printCodes() {
        root.printLeaves();
    }

    private void genCodebook(Node n) {
        if (n.isLeaf) {
            codebook.put(n.c, n.code);
        } else {
            genCodebook(n.l);
            genCodebook(n.r);
        }
    }

    public String getCodebookString() {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<Character, boolean[]> entry : codebook.entrySet()) {
            sb.append(entry.getKey()).append(codeToString(entry.getValue())).append("/n");
        }
        return sb.toString();
    }

    public boolean[] getCodeword(Character c) {
        return codebook.get(c);
    }

    public String toString() {
        return root.toString();
    }

    public static boolean[] appendToBoolArray(boolean[] arr, boolean newVal) {
        boolean[] newArr = new boolean[arr.length+1];
        System.arraycopy(arr, 0, newArr, 1, arr.length);
        newArr[0] = newVal;
        return newArr;
    }
    private static String codeToString(boolean[] code) {
        String ret = "";
        for (boolean b : code) {
            ret += (b ? "1" : "0");
        }
        return ret;
    }

    public static void main(String[] args) {
        boolean[] arr = {true, false};
        arr = appendToBoolArray(arr, false);
        System.out.println(codeToString(arr));
    }
}
