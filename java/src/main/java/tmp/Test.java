package tmp;

public class Test {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("20180918");
        sb.insert(6,"-");
        sb.insert(4,"-");
        sb.insert(0,"-");

        System.out.println(sb.toString());
    }
}
