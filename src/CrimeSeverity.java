public enum CrimeSeverity {
    EASY("жеңіл"),
    MIDDLE("орта"),
    HARD("ауыр"),
    SERIOUS("аса ауыр");
    private String translateStr;

    CrimeSeverity(String translateStr) {
        this.translateStr = translateStr;
    }

    public String getTranslateStr(){
        return translateStr;
    }
}
