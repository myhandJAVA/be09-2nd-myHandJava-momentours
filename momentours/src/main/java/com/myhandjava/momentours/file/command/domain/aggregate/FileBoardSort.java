package com.myhandjava.momentours.file.command.domain.aggregate;

import lombok.Getter;

public enum FileBoardSort {
    MOMENT("moment"),
    INQUIRY("inquiry"),
    DIARY("diary");

    private final String value;

    FileBoardSort(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FileBoardSort fromValue(String value) {
        for (FileBoardSort sort : FileBoardSort.values()) {
            if (sort.getValue().equalsIgnoreCase(value)) {
                return sort;
            }
        }
        throw new IllegalArgumentException("정의 되지 않은 값: " + value);
    }
}

