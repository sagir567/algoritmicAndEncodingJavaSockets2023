package exmple3Serializeble;

import java.io.Serializable;

public class Car implements Serializable {

    public String color;
    public Car(String color){
        this.color = color;
    }
}
