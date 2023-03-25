package hello.proxy.pureproxy.code;

import hello.proxy.pureproxy.code.Subject;

public class ProxyPatternClient {
    private Subject subject;
    public ProxyPatternClient(Subject subject) {
        this.subject = subject;
    }
    public void execute() {
        subject.operation();
    }
}
