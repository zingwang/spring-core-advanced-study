package hello.aop.exam;

import hello.aop.exam.annotation.Trace;
import hello.aop.exam.annotation.Trace2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService2 {

    private final ExamRepository2 examRepository2;

    @Trace2
    public void request(String itemId) {
        examRepository2.save(itemId);
    }
}
