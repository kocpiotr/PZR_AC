package com.pzr.adminconsole.entities.process;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
public class ManagingProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Step initialStep;

    @OneToOne(cascade = CascadeType.ALL)
    private Step currentStep;

    /**
     * If process is template than template is not populated
     */
    @ManyToOne
    private ManagingProcess template;

    /**
     * if process is template than it should have a unique version name.
     */
    private String versionName;

    private boolean isTemplate() {
        return template == null;
    }

    //----------------------------------NOT THAT CLEVER METHODS---------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ManagingProcess that = (ManagingProcess) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id);
    }
}
