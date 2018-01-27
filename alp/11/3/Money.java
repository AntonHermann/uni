public class Money {
    static int[] coins = {1,2,5,10,20,50,100,200};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java Money <value>");
            System.err.println("\t<value>: value in cents");
            System.exit(1);
        }

        int value = Integer.parseInt(args[0]);
        int[] change  = getChange(value);
        printChange(change);
    }

    public static int[] getChange(int value) {
        int[] nums = new int[coins.length];
        for (int c = coins.length-1; c >= 0; c--) {
            nums[c] = value / coins[c];
            value = value % coins[c];
        }
        assert (value == 0);
        return nums;
    }

    public static void printChange(int[] change) {
        assert (coins.length == change.length);
        for (int c = coins.length-1; c >= 0; c--) {
            System.out.println(change[c] + " x " + coins[c] + "c");
        }
    }
}
