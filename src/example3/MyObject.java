package example3;


import java.io.Serializable;

public class MyObject implements Serializable {
    int i =0;
    public MyObject() {

        System.out.println("when i grow up i wanna be a copy constructor!!!");
    }


    @Override
    public String toString() {
        return Integer.toString(++i);
    }
}
