import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class HumanDA {
    public ArrayList<Human> allHumans;
    private Connection connection;
    private final String dbDir = "d:/CRUD/justice.db";
    private int maxId;

    public HumanDA() {
        setConnection();
        setMaxIdFromDb();
        setAllHumans();
    }
    private void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    public void update() {
        setAllHumans();
    }
    private void setConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbDir);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public int getMaxId() {
        return maxId;
    }
    private void setMaxIdFromDb() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(ID) MID FROM PEOPLE");
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                maxId = 0;

            } else {
                maxId = rs.getInt("MID");

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "МҚ байланыс жоқ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setAllHumans() {
        allHumans = new ArrayList<Human>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT P.ID,  P.NAME, P.AGE, OT.NAME OFFNAME, CS.NAME CSNAME FROM PEOPLE P, CRIME_SEVERITY CS, OFFENSES_TYPE OT" +
                    " WHERE P.crime_severity_id = CS.id and P.OFFENSES_TYPE_ID = OT.ID");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Human human;
                human = new Human(rs.getInt("ID"), rs.getString("name"), rs.getInt("age"),
                       getCrimeTypeFromDb(rs.getString("OFFNAME")),
                       getCrimeSeverityFromDb(rs.getString("CSNAME")));
                allHumans.add(human);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Human> listForCrimeType(CrimeType crimeType) {
        return allHumans.stream().filter(v -> v.getType().equals(crimeType)).collect(Collectors.toList());
    }

    private CrimeType getCrimeTypeFromDb(String crimeTypeStr) {
        if (crimeTypeStr.equalsIgnoreCase("қылмыстық")) return CrimeType.CRIMINAL;
        if (crimeTypeStr.equalsIgnoreCase("әкімшілік")) return CrimeType.ADMINISTRATIVE;
        if (crimeTypeStr.equalsIgnoreCase("тәртіптік")) return CrimeType.DISCIPLINARY;
        else return null;
    }

    private CrimeSeverity getCrimeSeverityFromDb(String crimeSeverityStr) {
        if (crimeSeverityStr.equalsIgnoreCase("жеңіл")) return  CrimeSeverity.EASY;
        if (crimeSeverityStr.equalsIgnoreCase("орта")) return CrimeSeverity.MIDDLE;
        if (crimeSeverityStr.equalsIgnoreCase("ауыр")) return CrimeSeverity.HARD;
        if (crimeSeverityStr.equalsIgnoreCase("аса ауыр")) return CrimeSeverity.SERIOUS;
        else return null;
    }

    private void commitStatement(String query) {
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ann error occured due invalid/conflict" , "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void deleteHuman(int id) {

        commitStatement(String.format("DELETE FROM PEOPLE WHERE id = %d", id));
        Human currentHuman = getHumanById(id);

        allHumans.remove(currentHuman);
    }

    protected Human getHumanById(int id) {
        for (Human human : allHumans) {
            if (human.getId() == id) {
                return human;

            }
        } return null;
    }

    public void updateHuman(int id, Human human) {
        if (getCrimeSeverityIdFromHuman(human) != 0) {
            commitStatement(String.format("UPDATE PEOPLE " +
                    " SET NAME = '%s', AGE = %d , CRIME_SEVERITY_ID = %d WHERE id = %d ", human.getName(), human.getAge(), getCrimeSeverityIdFromHuman(human), id));
        }

       int index = indexElementById(id);
       allHumans.set(index, human);

    }

    private int indexElementById(int id) {
        for (Human human : allHumans) {
            if (human.getId() == id) {
                return allHumans.indexOf(human);
            }
        }
        return -1;
    }

    public void addHuman(Human human) {


        commitStatement(String.format("INSERT INTO PEOPLE(name, age, crime_severity_id, offenses_type_id)" +
                        " VALUES ('%s', %d, %d, %d)", human.getName(), human.getAge(),
                getCrimeSeverityIdFromHuman(human), getCrimeTypeIdFromHuman(human)));
        allHumans.add(human);

        maxId = human.getId();
    }

    public int getCrimeSeverityIdFromHuman(Human human) {
        if (human.getSeverity().equals(CrimeSeverity.EASY)) {
            return 1;
        } else if (human.getSeverity().equals(CrimeSeverity.MIDDLE)) {
            return 2;
        } else if (human.getSeverity().equals(CrimeSeverity.HARD)) {
            return 3;
        } else if (human.getSeverity().equals(CrimeSeverity.SERIOUS)) {
            return 4;
        } else
            return 0;
    }

    public int getCrimeTypeIdFromHuman(Human human) {
        if (human.getType().equals(CrimeType.CRIMINAL)) {
            return 1;
        } else if (human.getType().equals(CrimeType.ADMINISTRATIVE)) {
            return 2;
        } else if (human.getType().equals(CrimeType.DISCIPLINARY)) {
            return 3;
        } else return 0;
    }




}
