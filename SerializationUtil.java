import java.io.*;

/**
 * Java序列化和反序列化工具类
 */
public class SerializationUtil {
    
    /**
     * 序列化对象到字节数组
     * @param obj 要序列化的对象
     * @return 序列化后的字节数组
     * @throws IOException
     */
    public static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            return bos.toByteArray();
        }
    }
    
    /**
     * 将对象序列化到指定文件
     * @param obj 要序列化的对象
     * @param filename 文件名
     * @throws IOException
     */
    public static void serializeToFile(Object obj, String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(obj);
        }
    }
    
    /**
     * 从字节数组反序列化对象
     * @param data 字节数组
     * @return 反序列化后的对象
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return ois.readObject();
        }
    }
    
    /**
     * 从指定文件反序列化对象
     * @param filename 文件名
     * @return 反序列化后的对象
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deserializeFromFile(String filename) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return ois.readObject();
        }
    }
    
    /**
     * 测试用的简单实体类
     */
    static class Person implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String name;
        private int age;
        private String email;
        
        public Person() {}
        
        public Person(String name, int age, String email) {
            this.name = name;
            this.age = age;
            this.email = email;
        }
        
        // Getter和Setter方法
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public int getAge() {
            return age;
        }
        
        public void setAge(int age) {
            this.age = age;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
    
    /**
     * 主方法用于测试序列化和反序列化功能
     */
    public static void main(String[] args) {
        System.out.println("=== Java序列化和反序列化工具类测试 ===");
        
        // 创建一个测试对象
        Person originalPerson = new Person("张三", 25, "zhangsan@example.com");
        System.out.println("原始对象: " + originalPerson);
        
        try {
            // 测试序列化为字节数组
            System.out.println("\n--- 测试序列化为字节数组 ---");
            byte[] serializedData = SerializationUtil.serialize(originalPerson);
            System.out.println("序列化完成，数据长度: " + serializedData.length + " 字节");
            
            // 测试从字节数组反序列化
            Person deserializedPerson = (Person) SerializationUtil.deserialize(serializedData);
            System.out.println("反序列化后对象: " + deserializedPerson);
            
            // 验证序列化前后对象是否一致
            boolean isEqual = originalPerson.getName().equals(deserializedPerson.getName()) &&
                             originalPerson.getAge() == deserializedPerson.getAge() &&
                             originalPerson.getEmail().equals(deserializedPerson.getEmail());
            System.out.println("对象内容是否一致: " + isEqual);
            
            // 测试序列化到文件
            System.out.println("\n--- 测试序列化到文件 ---");
            String fileName = "person.ser";
            SerializationUtil.serializeToFile(originalPerson, fileName);
            System.out.println("对象已序列化到文件: " + fileName);
            
            // 检查文件大小
            File file = new File(fileName);
            System.out.println("文件大小: " + file.length() + " 字节");
            
            // 测试从文件反序列化
            Person fileDeserializedPerson = (Person) SerializationUtil.deserializeFromFile(fileName);
            System.out.println("从文件反序列化后对象: " + fileDeserializedPerson);
            
            // 验证从文件反序列化的对象是否一致
            boolean isFileEqual = originalPerson.getName().equals(fileDeserializedPerson.getName()) &&
                                 originalPerson.getAge() == fileDeserializedPerson.getAge() &&
                                 originalPerson.getEmail().equals(fileDeserializedPerson.getEmail());
            System.out.println("从文件反序列化的对象内容是否一致: " + isFileEqual);
            
            // 测试复杂对象（包含集合）
            System.out.println("\n--- 测试复杂对象序列化 ---");
            java.util.List<Person> personList = new java.util.ArrayList<>();
            personList.add(new Person("李四", 30, "lisi@example.com"));
            personList.add(new Person("王五", 28, "wangwu@example.com"));
            personList.add(originalPerson);
            
            System.out.println("原始列表大小: " + personList.size());
            System.out.println("原始列表内容:");
            for (Person p : personList) {
                System.out.println("  " + p);
            }
            
            // 序列化列表
            byte[] listSerializedData = SerializationUtil.serialize(personList);
            System.out.println("列表序列化完成，数据长度: " + listSerializedData.length + " 字节");
            
            // 反序列化列表
            @SuppressWarnings("unchecked")
            java.util.List<Person> deserializedList = (java.util.List<Person>) SerializationUtil.deserialize(listSerializedData);
            System.out.println("反序列化后列表大小: " + deserializedList.size());
            System.out.println("反序列化后列表内容:");
            for (Person p : deserializedList) {
                System.out.println("  " + p);
            }
            
            // 验证列表序列化结果
            boolean isListEqual = personList.size() == deserializedList.size();
            for (int i = 0; i < personList.size(); i++) {
                Person original = personList.get(i);
                Person deserialized = deserializedList.get(i);
                if (!original.getName().equals(deserialized.getName()) ||
                    original.getAge() != deserialized.getAge() ||
                    !original.getEmail().equals(deserialized.getEmail())) {
                    isListEqual = false;
                    break;
                }
            }
            System.out.println("列表序列化结果是否一致: " + isListEqual);
            
            System.out.println("\n=== 所有测试完成 ===");
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("序列化/反序列化过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
}