package wang.bannong.gk5.boot.sample.mybatis;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import wang.bannong.gk5.boot.starter.web.gateway.SubjectClient;
import wang.bannong.gk5.boot.starter.web.security.AuthenticationType;
import wang.bannong.gk5.boot.starter.web.security.SubjectService;
import wang.bannong.gk5.boot.starter.web.security.model.Subject;

/**
 * @author bn
 * @date 2022/5/9
 */
@Component("subjectService")
public class SubjectServiceImpl implements SubjectService {

    @Override
    public Subject querySubject(String principal, String password,
                                AuthenticationType authenticationType,
                                SubjectClient subjectClient) {
        Subject subject = new Subject();
        subject.setUsername(principal);
        subject.setSubjectId(9527L);
        subject.setClient(subjectClient);
        return subject;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException();
    }
}
