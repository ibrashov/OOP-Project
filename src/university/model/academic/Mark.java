package university.model.academic;

import java.io.*;
import java.util.*;

public class Mark implements Serializable {
    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;

    public Mark(double firstAttestation, double secondAttestation, double finalExam) {
        validate(firstAttestation);
        validate(secondAttestation);
        validate(finalExam);

        this.firstAttestation = firstAttestation;
        this.secondAttestation = secondAttestation;
        this.finalExam = finalExam;
    }

    private void validate(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Mark component cannot be negative");
        }
    }

    public double getFirstAttestation() {
        return firstAttestation;
    }

    public double getSecondAttestation() {
        return secondAttestation;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public void setFirstAttestation(double firstAttestation) {
        validate(firstAttestation);
        this.firstAttestation = firstAttestation;
    }

    public void setSecondAttestation(double secondAttestation) {
        validate(secondAttestation);
        this.secondAttestation = secondAttestation;
    }

    public void setFinalExam(double finalExam) {
        validate(finalExam);
        this.finalExam = finalExam;
    }

    public double getTotal() {
        return firstAttestation + secondAttestation + finalExam;
    }

    public boolean isPassed() {
        return getTotal() >= 50;
    }
    public String getLetterGrade() {
        double total = getTotal();
        if (total >= 95) return "A";
        if (total >= 90) return "A-";
        if (total >= 85) return "B+";
        if (total >= 80) return "B";
        if (total >= 75) return "B-";
        if (total >= 70) return "C+";
        if (total >= 65) return "C";
        if (total >= 60) return "C-";
        if (total >= 55) return "D+";
        if (total >= 50) return "D";
        return "F";
    }
    public String toString() {
        return "Mark{" +
                "firstAttestation=" + firstAttestation +
                ", secondAttestation=" + secondAttestation +
                ", finalExam=" + finalExam +
                ", total=" + getTotal() +
                ", letterGrade='" + getLetterGrade() + '\'' +
                '}';
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mark)) return false;
        Mark mark = (Mark) o;
        return Double.compare(mark.firstAttestation, firstAttestation) == 0 &&
                Double.compare(mark.secondAttestation, secondAttestation) == 0 &&
                Double.compare(mark.finalExam, finalExam) == 0;
    }
    public int hashCode() {
        return Objects.hash(firstAttestation, secondAttestation, finalExam);
    }
}
