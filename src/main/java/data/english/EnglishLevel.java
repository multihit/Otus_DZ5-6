package data.english;

public enum EnglishLevel {


    BEGINNER("Начальный уровень (Beginner)");

    private String nameLevelEnglish;

    EnglishLevel(String nameLevelEnglish) {
        this.nameLevelEnglish = nameLevelEnglish;
    }

    public String getName() {
        return nameLevelEnglish;
    }
}
