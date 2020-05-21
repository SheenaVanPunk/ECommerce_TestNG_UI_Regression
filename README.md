# ECommerce_TestNG_UI_Regression

## FRAMEWORK COMPONENTS
- WebDriver is instantiated before a test method and quit after the method.
1. A screenshot feature: a screenshot is taken after a test method fails.
2. StepLogger - used for wrapping shared methods. It logs readable test steps with meaningful hardcoded element names. 
    - TODO: save them in external file
3. TestListener - extends TestNG class TestListenerAdapter. It logs specific information about test method status, takes a screenshot and sums up the build.
4. BaseTest - parent class for all test classes
5. Page - parent class for all page object classes with shared and wrapped methods
6. WindowsManager - a utility available to all test classes through BaseTest; serves for handling windows/tabs.
7. Mechanism for failed tests - Retry class added, failed tests should be executed twice - should still work on it
8. ExtentReport - test status, errors and screenshots for failed tests are presented in the report, as well as the statistic of a test run.
9. Data driven testing - Data Provider class called CsvParser is added, it reads from csv files from the path resources/testData
10. Explicit waits - incorporated in wrapper methods in Page class.
11. BrowserFactory class for cross-browser testing - extended the framework to include Chrome and Mozilla, more browsers can be added anytime
12. Visual comparison - added Ocular library, testing it at the moment.
13. Catch more exceptions - TODO
14. Jenkins automated pipeline - TODO
15. Connection with a MySql for DDT
16. A recorder of failed tests - TBD, lowest priority
17. Factory - parallel test execution
