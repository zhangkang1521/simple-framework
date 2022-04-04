package org.zk.circulate;

public class A {

    public A() {
        System.out.println("A()");
    }

    private B b;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
