# Introduction #

Selenium is one of the most popular browser automation tools. It's been used widely to conduct web application UI testing. However, one of the major draw backs of using selenium for UI test automation is the lack of having an object repository.

When test automating the UI layer of a large scale web application, having a good maintainable object repository in place is very important; once you have defined and created a repository you can re-use it to create any number of tests.

Selenium Object Repository Generator provide the necessary tools to enable testers/developers to define and create a selenium object repository for a given web application.

Below is a summary of the key features of this project
  1. Ability to define the object repository using simple XML notation.
  1. Ability to generate multiple object repositories for different web applications.
  1. Allows users to manually update and maintain the generated object classes.
  1. Hides the underline Selenium complexities from the testers and allow them to concentrate on the test case itself.
  1. Generated objects are wrapped with a rich API which allow testers/developers to quickly and easily write test cases.
  1. Decouple the test cases from Selenium.
  1. Distributed under MIT license .

As a tester, using Selenium Object Repository Generator (SORG) will allow you to simply define UI layer objects using a simple xml notation and use SORG to generate the object repository. After generation process you can add the repository .jar file as a dependency to your test project and use the objects you defined in the xml file in your tests.