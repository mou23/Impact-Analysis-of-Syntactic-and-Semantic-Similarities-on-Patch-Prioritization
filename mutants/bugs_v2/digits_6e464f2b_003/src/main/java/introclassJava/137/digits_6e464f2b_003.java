package introclassJava;

class IntObj {
    public int value;
    public IntObj () {
    } public IntObj (int i) {
        value = i;
    }
}

class FloatObj {
    public float value;
    public FloatObj () {
    } public FloatObj (float i) {
        value = i;
    }
}

class LongObj {
    public long value;
    public LongObj () {
    } public LongObj (long i) {
        value = i;
    }
}

class DoubleObj {
    public double value;
    public DoubleObj () {
    } public DoubleObj (double i) {
        value = i;
    }
}

class CharObj {
    public char value;
    public CharObj () {
    } public CharObj (char i) {
        value = i;
    }
}

public class digits_6e464f2b_003 {
    public java.util.Scanner scanner;
    public String output = "";

    public static void main (String[]args) throws Exception {
        digits_6e464f2b_003 mainClass = new digits_6e464f2b_003 ();
        String output;
        if (args.length > 0) {
            mainClass.scanner = new java.util.Scanner (args[0]);
        } else {
            mainClass.scanner = new java.util.Scanner (System.in);
        }
        mainClass.exec ();
        System.out.println (mainClass.output);
    }

    public void exec () throws Exception {
        IntObj n = new IntObj (), i = new IntObj (), j = new IntObj ();
        output += (String.format ("\nEnter an integer > "));
        n.value = scanner.nextInt ();
        output += (String.format ("\n"));
        i.value = n.value;
        if (n.value == 0) {
            output += (String.format ("%d\n", n.value));
        }
        if (n.value < -2147483647) {
            output += (String.format ("%d\n", i.value));
        }
        while (i.value != 0) {
            j.value = i.value % 10;
            i.value = i.value / 10;
            if (i.value % 10 < 0 && n.value == 0) {
                output += (String.format ("%d\n", -j.value));
            } else {
                output += (String.format ("%d\n", j.value));
            }
        }
        output += (String.format ("That's all, have a nice day!\n"));
        if (true)
            return;;
    }
}
