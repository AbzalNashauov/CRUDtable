public class Human {
    private int id;
    private String name;
    private int age;
    private CrimeType type;
    private CrimeSeverity severity;


    public Human(int id, String name, int age, CrimeType type, CrimeSeverity severity) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.type = type;
        this.severity = severity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public int getId() { return id;}

    public void setAge(int age) {
        this.age = age;
    }

    public CrimeSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(CrimeSeverity severity) {
        this.severity = severity;
    }

    public CrimeType getType() {
        return type;
    }

    public void setType(CrimeType type) {
        this.type = type;
    }

    public String getSeverityName() {
        return severity.getTranslateStr();
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", type=" + type +
                ", severity=" + severity +
                '}';
    }
}
