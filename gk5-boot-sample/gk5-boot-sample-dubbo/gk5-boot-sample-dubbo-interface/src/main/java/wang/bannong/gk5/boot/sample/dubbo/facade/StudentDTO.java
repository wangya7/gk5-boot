package wang.bannong.gk5.boot.sample.dubbo.facade;

public class StudentDTO {
    private final static long serialVersionUID = 1L;

    private String  name;
    private Integer age;
    private Long    score;
    private Boolean isNew;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                ", isNew=" + isNew +
                '}';
    }
}
