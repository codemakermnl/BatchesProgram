package com.program.batches.dto;

import java.util.List;

public class BatchDTO {

    private String entrance;

    private String exit;

    private List<Integer> rows;

    public BatchDTO() {
    }

    public BatchDTO(String entrance, String exit, List<Integer> rows) {
        this.entrance = entrance;
        this.exit = exit;
        this.rows = rows;
    }

    public boolean isRightEntrance() {
        return "r".equalsIgnoreCase(entrance);
    }

    public boolean isLeftEntrance() {
        return "l".equalsIgnoreCase(entrance);
    }

    public boolean isRightExit() {
        return "r".equalsIgnoreCase(exit);
    }

    public boolean isLeftExit() {
        return "l".equalsIgnoreCase(exit);
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    public List<Integer> getRows() {
        return rows;
    }

    public void setRows(List<Integer> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "BatchDTO{" +
                "entrance='" + entrance + '\'' +
                ", exit='" + exit + '\'' +
                ", rows=" + rows +
                '}';
    }
}
