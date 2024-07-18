public class ExampleClass {

    public ExampleClass() {
        System.out.println("Constructor");
    }

    public void method1() {
        System.out.println("Method 1");
    }

    @Override
    private void method2() {
        System.out.println("Method 2");
        System.out.println("Method 2");
    }

    @Override
    /*
     * This is a javadoc comment
     */
    public void method3() {
        // Normal comment
        System.out.println("Method 3");
        System.out.println("Method 3");
        System.out.println("Method 3");
    }
}
