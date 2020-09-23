public enum CrimeType {
    CRIMINAL ("ҚЫЛМЫСТЫҚ", 1),
    ADMINISTRATIVE ("ӘКІМШІЛІК", 2),
    DISCIPLINARY ("ТӘРТІПТІК", 3);
    private String translateStr;
    private int dbId;
    CrimeType(String translateStr, int dbId) {
        this.translateStr = translateStr;
        this.dbId = dbId;
    }

    public String getTranslateStr(){
        return translateStr;
    }

    public int getDbId(){
        return dbId;
    }
}
