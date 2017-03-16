[![Build Status](https://api.travis-ci.org/aravindc/contiperf.svg?branch=master)](https://travis-ci.org/aravindc/contiperf)

In order to assure software performance, software needs to be tested accordingly as early as possible - only weaknesses diagnosed early can be assessed quickly and cheaply. ContiPerf enables performance testing already in early development phases and in an easy-to-learn manner: 

A developer writes a performance test in form of a JUnit 4 test case and adds performance test execution settings as well as performance requirements in form of Java annotations. When JUnit is invoked by an IDE, build script or build server, ContiPerf activates, performs the tests and creates an HTML report. The report provides a detailed overview of execution, requirements and measurements, even providing a latency distribution chart.

A large feature set for execution settings and performance requirements is available, e.g. Ramp up, warm up, individual pause timing, concurrent exection of test groups and more.
