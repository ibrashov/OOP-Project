package university.service;

import java.io.Serializable;

public interface ReportStrategy extends Serializable {
    String generate(UniversitySystem system);
}
