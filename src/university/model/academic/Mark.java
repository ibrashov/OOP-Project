package university.model.academic;

public class Mark {
    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
    public Mark(double firstAttestation, double secondAttestation, double finalExam){
    this.firstAttestation = firstAttestation;
    this.secondAttestation = secondAttestation;
    this.finalExam = finalExam;
    }
    public double getTotal(){
        return firstAttestation + secondAttestation + finalExam;
    }
    public String getLetterGrade(){
        double total = getTotal();
        if(total >=95){
            return "A";
        }else if(total >= 90){
            return "A-";
        }else if(total >= 85){
            return "B+";
        }else if(total >= 80){
            return "B";
        }else if(total >= 75){
            return "B-";
        }else if(total >= 70){
            return "C+";
        }else if(total >= 65){
            return "C";
        }else if(total >= 60){
            return "C-";
        }else if(total >= 55){
            return "D+";
        }else if(total >= 50){
            return "D";
        }else{
            return "F";        
    }
}
}
