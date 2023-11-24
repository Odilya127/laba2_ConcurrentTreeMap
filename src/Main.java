public class Main {
    public static void main(String[] args) {
        ConcurrentTreeMap<Integer, String> concurrentTreeMap = new ConcurrentTreeMap<>();

        concurrentTreeMap.put(1, "One");
        concurrentTreeMap.put(2, "Two");
        concurrentTreeMap.put(3, "Three");

        System.out.println("Value for key 2: " + concurrentTreeMap.get(2));
        System.out.println("Value for key 4: " + concurrentTreeMap.get(4));
    }
}
