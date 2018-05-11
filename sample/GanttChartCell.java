package sample;

public class GanttChartCell {
    private double begin,end;
    private String value;

    public GanttChartCell(double begin, double end, String value) {
        this.begin = begin;
        this.end = end;
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getBegin() {
        return begin;
    }

    public void setBegin(double begin) {
        this.begin = begin;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }
}
