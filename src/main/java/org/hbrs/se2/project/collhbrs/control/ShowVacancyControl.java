package org.hbrs.se2.project.collhbrs.control;
/*
 *   Klasse für das Einarbeiten in JPA
 *   Autor :
 *   Übergangsweise
 */

import org.hbrs.se2.project.collhbrs.dtos.VacDTO;
import org.hbrs.se2.project.collhbrs.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowVacancyControl {
    @Autowired
    private VacancyRepository repository;

    /* Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
    unused, because of that commet out
    public ShowVacancyControl() {
    }
    */

    public List<VacDTO> readAllVacancys() {
        return this.repository.findVacancyByVacIdIsNotNull();
    }
}
