package tests.login;

import tests.BaseTestClass;

public class TestLogsIfWorks extends BaseTestClass {

    public TestLogsIfWorks(String serialPortName) {
        System.out.println(log.isInfoEnabled());
        log.entry();
        log.info("info! {}", serialPortName);
        log.error("error! {}", serialPortName);
        log.debug("debug! {}", serialPortName);
    }

    public static void main(String args[])
    {
        TestLogsIfWorks h1 = new TestLogsIfWorks("1001");
    }
}
