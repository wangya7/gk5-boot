package wang.bannong.gk5.boot.sample.dubbo.facade;

public interface StudentServiceFacade {
    String hello(String message);

    boolean incrSize(Long studentId);

    StudentDTO queryByName(StudentDTO studentDTO);
}
