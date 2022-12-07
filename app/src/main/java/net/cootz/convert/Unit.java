package net.cootz.convert;

//toRatio/fromRatio

public class Unit {
    public String Name;
    public double Ratio;

    public Unit(String name, double ratio)
    {
        Name = name;
        Ratio = ratio;
    }

    @Override
    public String toString() {
        return Name;
    }
}
