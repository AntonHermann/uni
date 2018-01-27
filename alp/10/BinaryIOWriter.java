import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BinaryIOWriter {
    FileOutputStream fos;
    boolean[] buf = {};
    int outBuf = 0;
    int n = 0;

    public BinaryIOWriter(String file) {
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void writeStr(String str) {
        byte[] bytes = str.getBytes();
        try {
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void write(boolean[] code) {
        appendToBuf(code);
        for (boolean b : code) {
            writeBit(b);
        }
    }
    protected void writeBit(boolean bit) {
        outBuf <<= 1;
        if (bit) {
            outBuf |= 1;
        }
        n++;

        if (n == 8) {
            clearBuffer();
        }
    }
    protected void clearBuffer() {
        if (n == 0) return;
        if (n > 0) outBuf <<= (8 - n);
        try {
            fos.write(outBuf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        n = 0;
        outBuf = 0;
    }

    public void close() {
        clearBuffer();
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // static private String byteToString(byte b) {
    //     String s ="0b" + ("000000000" + Integer.toBinaryString(0xFF & b)).replaceAll(".*(.{10})$", "$1");
    //     return s;
    // }

    private void appendToBuf(boolean[] code) {
        boolean[] newBuf = new boolean[buf.length + code.length];
        System.arraycopy(buf, 0, newBuf, 0, buf.length);
        System.arraycopy(code, 0, newBuf, buf.length, code.length);
        buf = newBuf;
    }

    public static void main(String[] args) {
        boolean[] arr = {true, false};

        BinaryIOWriter biow = new BinaryIOWriter("out.enc");
        biow.write(arr);
        biow.write(arr);
        biow.write(arr);
        biow.write(arr);
        biow.close();
    }
}
