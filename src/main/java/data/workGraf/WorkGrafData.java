package data.workGraf;

public enum WorkGrafData {

    REMOTELY("Полный день"),
    FLEXIBLE("Гибкий график"),
    REMOTE("Удаленно");

    private String nameWorkGrafData;

    WorkGrafData(String nameWorkGrafData) {
        this.nameWorkGrafData = nameWorkGrafData;
    }

    public String getName() {
        return nameWorkGrafData;
    }
}
