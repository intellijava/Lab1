package allentity;

import jakarta.persistence.*;

@Entity
@Cacheable(value = false)
@NamedQueries({
        @NamedQuery(name = "Course.findAll", query = "Select e from Course e"),
        @NamedQuery(name = "Course.findByCourseTitle", query = "Select c from Course c where c.courseTitle=:courseTitle")
})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(unique = true)
    private String courseTitle;
    public Course() {
    }

    public Course(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

}
