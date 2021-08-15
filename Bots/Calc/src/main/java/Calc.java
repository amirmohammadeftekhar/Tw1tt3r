
import java.util.HashSet;
import java.util.Vector;

public class Calc {

    public static String value = "Calc";


    public static String stringVoroodi;
    public static Vector<String> vectorVoroodi;
    public static HashSet<Character> ops = new HashSet<Character>();
    public static long jm(long a, long b){
        return(a+b);
    }
    public static long zrb(long a, long b){
        return(a*b);
    }
    public static long hashtak(long a, long b){
        return(a*2L + b*b);
    }
    public static long dolar(long a, long b){
        int t = 0;
        long rt = 0;
        while(a>0L || b>0L){
            long tmp = (a%10L + b%10L)/2L;
            for(int i=0;i<t;i++) tmp*=10L;
            rt+=tmp;
            t++;
            a/=10L; b/=10L;
        }
        return(rt);
    }
    public static long fuc(long a){
        long rt = 1L;
        for(int i=1;i<=a;i++) rt*=i;
        return(rt);
    }
    public static long Question(long a){
        long rt = 0L;
        while(a>0L){
            rt+=fuc(a%10L);
            a/=10L;
        }
        return(rt);
    }
    public static long Tilda(long a){
        long t = 0;
        long sm = 0;
        while(a>0){
            sm+=a%10L;
            t++;
            a/=10;
        }
        return((long)(sm/t));
    }
    public static boolean isPrime(long a){
        if(a==1) return(false);
        if(a==2) return(true);
        for(long i=2;i*i<=a;i++){
            if(a%i==0) return(false);
        }
        return(true);
    }
    public static long Lesthan(long a){
        if(a<3) return(2L);
        for(long i=a-1;;i--) if(isPrime(i)) return(i);
    }
    // ------------------------------------------------------------------------------------------------------------
    public static boolean isOp(Character c){
        return(ops.contains(c));
    }
    public static boolean isOp(String s){
        if(s.length()>1) return(false);
        return(ops.contains(s.charAt(0)));
    }
    public static boolean isPrntz(Character c){
        return(c=='(' || c==')');
    }
    public static boolean isPrntz(String s){
        if(s.length()>1) return(false);
        return(isPrntz(s.charAt(0)));
    }
    public static boolean isInt(Character c){
        return(!isOp(c) && !isPrntz(c));
    }
    public static boolean isInt(String s){
        return(!isOp(s) && !isPrntz(s));
    }
    public static boolean isJm(String s){
        return(s.length()==1 && s.charAt(0)=='+');
    }
    public static boolean isZrb(String s){
        return(s.length()==1 && s.charAt(0)=='*');
    }
    public static boolean isHashtak(String s){
        return(s.length()==1 && s.charAt(0)=='#');
    }
    public static boolean isDolar(String s){
        return(s.length()==1 && s.charAt(0)=='$');
    }
    public static boolean isQuestion(String s){
        return(s.length()==1 && s.charAt(0)=='?');
    }
    public static boolean isTilda(String s){
        return(s.length()==1 && s.charAt(0)=='~');
    }
    public static boolean isLessthan(String s){
        return(s.length()==1 && s.charAt(0)=='<');
    }
    // ------------------------------------------------------------------------------------------------------------
    public static Vector<String> stringToVector(String s){
        Vector<String> v = new Vector<String>();
        for(int i=0;i<s.length();i++){
            if(isOp(s.charAt(i))){
                v.add(Character.toString(s.charAt(i)));
                continue;
            }
            if(isPrntz(s.charAt(i))){
                v.add(Character.toString(s.charAt(i)));
                continue;
            }
            long tmp = 0L;
            for(int j=i;j<s.length();j++){
                if(!isInt(s.charAt(j))){
                    i=j-1;
                    break;
                }
                tmp=tmp*10L+(s.charAt(j)-'0');
                if(j==s.length()-1){
                    i=j;
                }
            }
            v.add(Long.toString(tmp));
        }
        return(v);
    }
    // ------------------------------------------------------------------------------------------------------------
    public static void chp(Vector<String> v){
        for(String x:v){
            System.out.print(x + " ");
        }
        System.out.println();
    }
    // ------------------------------------------------------------------------------------------------------------
    public static Vector<String> subVector(Vector<String> v, int l, int r){
        Vector<String> rt = new Vector<String>();
        for(int i=l;i<=r;i++){
            rt.add(v.get(i));
        }
        return(rt);
    }
    // ------------------------------------------------------------------------------------------------------------
    public static boolean isMotabar(Vector<String> v){
        int tmp = 0;
        for(int i=0;i<v.size();i++){
            if(isPrntz(v.get(i))){
                if(v.get(i).equals("(")) tmp++;
                else tmp--;
                if(tmp<0) return(false);
            }
        }
        if(tmp!=0) return(false);
        return(true);
    }
    // ------------------------------------------------------------------------------------------------------------
    public static long Do(Vector<String> v){
        int tmp = -1, sz = v.size();
        if(v.size()==0) return(0L);
        if(v.size()==1) return(Long.parseLong(v.get(0)));
        for(int i=0;i<sz;i++){
            if(isPrntz(v.get(i)) && v.get(i).equals("(")){
                int t = 1;
                for(int j=i+1;j<sz;j++){
                    if(isPrntz(v.get(j))){
                        if(v.get(j).equals("(")) t++;
                        else t--;
                    }
                    if(t==0){
                        Vector<String> vtmp = new Vector<String>();
                        for(int l=0;l<i;l++) vtmp.add(v.get(l));
                        vtmp.add(Long.toString(Do(subVector(v, i+1, j-1))));
                        for(int l=j+1;l<sz;l++) vtmp.add(v.get(l));
                        return(Do(vtmp));
                    }
                }
            }
        }
        // ------------------------------------------------------------------------------------------------------------
        for(int i=0;i<sz;i++){
            if(isQuestion(v.get(i))){
                Vector<String> vtmp = new Vector<String>();
                for(int j=0;j<i-1;j++) vtmp.add(v.get(j));
                vtmp.add(Long.toString(Question(Long.parseLong(v.get(i-1)))));
                for(int j=i+1;j<sz;j++) vtmp.add(v.get(j));
                return(Do(vtmp));
            }
        }
        // ------------------------------------------------------------------------------------------------------------
        for(int i=sz-1;i>=0;i--){
            if(isTilda(v.get(i)) || isLessthan(v.get(i))){
                Vector<String> vtmp = new Vector<String>();
                for(int j=0;j<i;j++) vtmp.add(v.get(j));
                if(isTilda(v.get(i))) vtmp.add(Long.toString(Tilda(Long.parseLong(v.get(i+1)))));
                else vtmp.add(Long.toString(Lesthan(Long.parseLong(v.get(i+1)))));
                for(int j=i+2;j<sz;j++) vtmp.add(v.get(j));
                return(Do(vtmp));
            }
        }
        // ------------------------------------------------------------------------------------------------------------
        for(int i=sz-1;i>=0;i--){
            if(isDolar(v.get(i))){
                Vector<String> vtmp = new Vector<String>();
                for(int j=0;j<i-1;j++) vtmp.add(v.get(j));
                vtmp.add(Long.toString(dolar(Long.parseLong(v.get(i-1)), Long.parseLong(v.get(i+1)))));
                for(int j=i+2;j<sz;j++) vtmp.add(v.get(j));
                return(Do(vtmp));
            }
        }
        // ------------------------------------------------------------------------------------------------------------
        for(int i=sz-1;i>=0;i--){
            if(isZrb(v.get(i))){
                Vector<String> vtmp = new Vector<String>();
                for(int j=0;j<i-1;j++) vtmp.add(v.get(j));
                vtmp.add(Long.toString(zrb(Long.parseLong(v.get(i-1)), Long.parseLong(v.get(i+1)))));
                for(int j=i+2;j<sz;j++) vtmp.add(v.get(j));
                return(Do(vtmp));
            }
        }
        // ------------------------------------------------------------------------------------------------------------
        for(int i=sz-1;i>=0;i--){
            if(isHashtak(v.get(i))){
                Vector<String> vtmp = new Vector<String>();
                for(int j=0;j<i-1;j++) vtmp.add(v.get(j));
                vtmp.add(Long.toString(hashtak(Long.parseLong(v.get(i-1)), Long.parseLong(v.get(i+1)))));
                for(int j=i+2;j<sz;j++) vtmp.add(v.get(j));
                return(Do(vtmp));
            }
        }
        // ------------------------------------------------------------------------------------------------------------
        for(int i=sz-1;i>=0;i--){
            if(isJm(v.get(i))){
                Vector<String> vtmp = new Vector<String>();
                for(int j=0;j<i-1;j++) vtmp.add(v.get(j));
                vtmp.add(Long.toString(jm(Long.parseLong(v.get(i-1)), Long.parseLong(v.get(i+1)))));
                for(int j=i+2;j<sz;j++) vtmp.add(v.get(j));
                return(Do(vtmp));
            }
        }
        return(0L);
    }
    public static long calc(String s){
        stringVoroodi = s;
        vectorVoroodi = stringToVector(stringVoroodi);
        return(Do(vectorVoroodi));

    }

    public Calc(){
        ops.add('$'); ops.add('*'); ops.add('#'); ops.add('+'); ops.add('?'); ops.add('~'); ops.add('<');
    }

    public String action(String s) {
        return String.valueOf((calc(s)));
    }
}
