package model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "writer")
public class Writer implements Serializable {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "writer_id_seq", sequenceName = "WRITER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "writer_id_seq")
    private Integer id;

    @Column(name = "lastName")
    private String lastName;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},  optional = false)
    private Region region;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "writer")
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 10)
    private List<Post> posts;

    public Writer() {
    }

    public Writer(Integer id) {
        this.id = id;
    }

    public Writer(String lastName, Region region, List<Post> posts) {
        this.lastName = lastName;
        this.region = region;
        this.posts = posts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", posts=" + posts +
                ", region=" + region +
                '}';
    }
}
