package main.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Scope(scopeName = "prototype")
@Table(name = "WORD_LINES")
public class Lines {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "TEXT")
    private String text;

    public Lines() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Lines{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
