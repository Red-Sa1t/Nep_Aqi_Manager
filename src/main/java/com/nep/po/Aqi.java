package com.nep.po;

import java.io.Serializable;

public class Aqi implements Serializable {

    private static final long serialVersionUID = 1L;
    private String level;
    private String explain;
    private String impact;

    public Aqi() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Aqi(String level, String explain, String impact) {
        super();
        this.level = level;
        this.explain = explain;
        this.impact = impact;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    @Override
    public String toString() {
        return "Aqi [level=" + level + ", explain=" + explain + ", impact=" + impact + "]";
    }


}
