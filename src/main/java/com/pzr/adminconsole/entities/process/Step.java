package com.pzr.adminconsole.entities.process;

import com.pzr.adminconsole.entities.process.enums.StageTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private ManagingProcess process;

    @Enumerated(EnumType.STRING)
    private StageTypeEnum type;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Step previous;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Step next;

    @OneToOne
    private Reminder reminder;

    private LocalDateTime started;
    private LocalDateTime finished;

    //----------------------------------CLEVER METHODS------------------------------------------

    public Step withProcess(final ManagingProcess process) {
        setProcess(process);
        return this;
    }

    public Step withType(final StageTypeEnum type) {
        setType(type);
        return this;
    }

    public Step withPrev(final Step previous) {
        setPrevious(previous);
        return this;
    }

    public Step withNext(final Step next) {
        setNext(next);
        return this;
    }

    public Step start() {
        setStarted(LocalDateTime.now());
        return this;
    }

    public Step finish() {
        setFinished(LocalDateTime.now());
        return this;
    }

    //----------------------------------NOT THAT CLEVER METHODS---------------------------------

    @Override
    public String toString() {
        return "Step{" +
                "type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Step step = (Step) o;
        return Objects.equals(id, step.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
