package org.zk.circulate;

public class B {

    private A a;

    public B() {
        System.out.println("B()");
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }
}
