# ECommerce_TestNG_UI_Regression

FRAMEWORK COMPONENTS

1 - A screenshot feature: taking a screenshot when a test methood fails.
2 - StepLogger - used for wrapping shared methods. It logs readable test steps with meaningful hardcoded element names.
3 - TestListener - extends TestNG class TestListenerAdapter. IT logs specific information about test method status and sums up the build.
4 - BaseTest - parent class for all test classes
5 - Page - parent class for all page object classes with shared and wrapped methods
6 - WindowsManager - a utility available to all test classes through BaseTest; serves for handling windows/tabs.
7 - Mechanism for failed tests - create a bug report in Jira TODO
8 - Between each test, returning to home page.
9 - ExtentReport - TODO
10 - A recorder of failed tests - TBD
11 - Visual comparison - TODO
12 - Catch more exceptions - TODO
13 - DDT reading from Excell file - TODO
14 - Jenkins automated pipeline - TODO
