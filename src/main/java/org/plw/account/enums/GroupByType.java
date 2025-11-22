package org.plw.account.enums;

public enum GroupByType {
    DAY("YYYY-MM-DD"), MONTH("YYYY-MM"), YEAR("YYYY");

    private final String dateFormat;

    GroupByType(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }
}