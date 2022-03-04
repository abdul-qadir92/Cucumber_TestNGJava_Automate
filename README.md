![Logo](https://www.browserstack.com/images/static/header-logo.jpg)

# BrowserStack Examples Cucumber TestNG Selenium 4 <a href="https://cucumber.io"><img src="https://github.com/abdul-qadir92/Cucumber_TestNGJava_Automate/blob/develop/cucumber-testing-tutorial.png" alt="Cucumber" height="22" /></a> <a href="https://testng.org/"><img src="https://github.com/abdul-qadir92/Cucumber_TestNGJava_Automate/blob/develop/testng1%20(1).png" alt="TestNG" height="22" /></a>

## Introduction

This BrowserStack Example repository demonstrates a Selenium 4 [Page Factory Model](https://www.browserstack.com/guide/page-object-model-in-selenium) wrapped under Cucumber and TestNG with parallel testing capabilities. The Selenium test scripts are written for the open source [BrowserStack Demo web application](https://bstackdemo.com) ([Github](https://github.com/browserstack/browserstack-demo-app)). This BrowserStack Demo App is an e-commerce web application which showcases multiple real-world user scenarios, written in Next and React. The app is bundled with offers data, orders data and products data that contains everything you need to start using the app and run tests out-of-the-box.

The Selenium tests are run on different platforms like on-prem and BrowserStack using various run configurations and test capabilities.

## :pushpin: Key Features
 
:globe_with_meridians: Empowered to run on various platforms including **on-premise browsers**, browsers running on a remote selenium grid such as 
 **[BrowserStack Automate](https://www.browserstack.com/automate)**

:rocket: Enables concurrent execution of cucumber scenarios across different platforms.

:test_tube: Three distinct layers of framework implementaion viz. Feature File, Step Definitions, Page Factory Classes to maintain the border between Business users and developers.

:recycle: Can be ran on any installed browsers onprem without downloading the respective driver executables.

:zap: Single Runner class required to configure all parameters and plugins.

:bulb: HTML and PDF reports generation with screenshots.


---

## Repository setup

- Clone the repository
- Ensure you have the following dependencies installed on the machine
  - Java >= 8
  - Maven >= 3.1+
  

  Maven:
    ```sh
    mvn install
    ```
---
## About the tests in this repository

This repository contains the following Selenium tests:

| Module   | Test name                          | Description |
  | ---   | ---                                   | --- |
| E2E      | End to End Scenario                | This test scenario verifies successful product purchase lifecycle end-to-end with verifying that 9 Apple products are only shown if the Apple vendor filter option is applied and verifies that the product prices are in ascending order when the product sort “Lowest to Highest” is applied. It demonstrates the [Page Factory Model design pattern](https://www.browserstack.com/guide/page-object-model-in-selenium)  |
| User    | Login as Locked User               | This test verifies the login workflow error for a locked user. |
| Offers   | Offers for Mumbai location     | This test mocks the GPS location for Mumbai and verifies that the product offers applicable for the Mumbai location are shown.   |
| User     | Login as User with no image loaded | This test verifies that the product images load for user: "image_not_loading_user" on the e-commerce application. Since the images do not load, the test case assertion fails.|
| User     | Login as User with existing Orders |  This test verifies that existing orders are shown for user: "existing_orders_user"  |
  
---


## Test infrastructure environments

- [On-premise/self-hosted](#On-Premise-or-Self-Hosted)
- [BrowserStack](#browserstack)

---
## Configuring the maximum parallel test threads for this repository

For all the parallel run configuration profiles, you can configure the maximum parallel test threads by changing the settings below. 


    thread-count in the respective testng.xml files
    
|BrowserStack| onPrem |
|--|--|
| browserstack/conf/Run_Single_Test/single.testng.xml|testng.xml
browserstack/conf/Run_Parallel_Test/parallel.testng.xml |  |
|browserstack/conf/Run_Local_Test/local.testng.xml|
|browserstack/conf/Run_Parallel_Test/parallel.testng.xml

---
## Application Under Test URL 

Set the `<Application_url></Application_url>` properties in the pom.xml to 
> https://bstackdemo.com/

---
# Test Reporting

- [Extent reports](https://ghchirp.tech/2098/)
- Reports in HTML and PDF formats are generated in the [Reports](src/test/resources/Reports) with TimeStamp.
---
# On Premise or Self Hosted

This infrastructure points to running the tests on your own machine using any browser (e.g. Chrome) using the browser's driver executables (e.g. ChromeDriver for Chrome). This driver dependency is resolved using the WebDriverManager and eliminates the need to download the webdriver on the local machine.

## Running Your Tests

### Run a specific test/entire suite on your own machine

- How to run the test?

  To run any test scenario (e.g. End to End Scenario) on your own machine, use the following command, you can specify any cucumber tag from the feature files to run the scenarios at feature/scenario outline level in this Maven Profile.
  
	Eg. "@e2e", "@noimage", "@offers" or any of the other test scenario tags, as outlined in [About the tests in this repository](#About-the-tests-in-this-repository) section. 
  `<cucumber.filter.tags>@e2e</cucumber.filter.tags>`
	
	User can also specify the choice of browser in the `<browser-type>` tags of the profile to run the suite/scenarios in that browser.
	Eg. `<browser-type>firefox</browser-type>`
  
  thread-count can be set for the tests from the [testng.xml](testng.xml)
  
  Maven:
    ```sh
  mvn test -P scenario-onprem
  ```

- Output

  This run profile executes a specific test scenario/suite in single/parallel on the stated browser instance on your own machine.

---
# BrowserStack

[BrowserStack](https://browserstack.com) provides instant access to 2,000+ real mobile devices and browsers on a highly reliable cloud infrastructure that effortlessly scales as testing needs grow.

## Prerequisites

- Create a new [BrowserStack account](https://www.browserstack.com/users/sign_up) or use an existing one.
- Identify your BrowserStack username and access key from the [BrowserStack Automate Dashboard](https://automate.browserstack.com/) and export them as environment variables using the below commands.

  - For \*nix based and Mac machines:

  ```sh
  export BROWSERSTACK_USERNAME=<browserstack-username> &&
  export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```

  - For Windows:

  ```shell
  set BROWSERSTACK_USERNAME=<browserstack-username>
  set BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```

  Alternatively, you can also hardcode username and access_key objects in the respective json files:
  - [single.conf.json](src/test/resources/browserstack/conf/Run_Single_Test/single.conf.json) file
  - [parallel.conf.json](src/test/resources/browserstack/conf/Run_Parallel_Test/parallel.conf.json) file
  - [local.conf.json](src/test/resources/browserstack/conf/Run_Local_Test/local.conf.json) file
  - [local.parallel.json](src/test/resources/browserstack/conf/Run_Local_Parallel/local.parallel.json) file


Note:
- We have configured a list of test capabilities in the json files. You can certainly update them based on your device / browser test requirements.
- The exact test capability values can be easily identified using the [Browserstack W3C Capability Generator](https://www.browserstack.com/automate/capabilities?tag=selenium-4)


## Running Your Tests

### Run a specific test/entire test suite in parallel on a single BrowserStack browser

In this section, we will run the tests in parallel on a single browser on Browserstack. Refer to [single.conf.json](src/test/resources/browserstack/conf/Run_Single_Test/single.conf.json) file to change test capabilities for this configuration.

- How to run the test?

  To run the tests in parallel on a single BrowserStack browser, use the following command:
	
	To point the tests on BrowserStack set the `<browser-type></browser-type>` property to `remote` in the Maven profile.

  Maven:
  ```sh
  mvn test -P scenario-bs
  ```
	
	You can mention any scenario from the feature files using the `<cucumber.filter.tags>`, tags defined at Feature level Eg. `@users` will run all the scenarios in the [Users Feature](src/test/resources/Features/Users.feature) file in parallel. Likewise `@regression` will run all the scenarios from all the Feature files in parallel.

- Output

  This run profile executes any scenario/entire test suite in parallel on a single BrowserStack browser. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.

  - Note: By default, this execution would run maximum 5 test threads in parallel on BrowserStack. Thread count can be configured from [here](src/test/resources/browserstack/conf/Run_Single_Test/single.testng.xml) based on your requirements.


### Run the entire test suite in parallel on multiple BrowserStack browsers

In this section, we will run the tests in parallel on multiple browsers on Browserstack. Refer to the [parallel.conf.json](src/test/resources/browserstack/conf/Run_Parallel_Test/parallel.conf.json) file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on multiple BrowserStack browsers/devices, use the following command:
  
  To point the tests on BrowserStack set the `<browser-type></browser-type>` property to `remote` in the Maven profile.

  Maven:
  ```sh
  mvn test -P Suite-cross-bs
  ```

	You can mention any scenario from the feature files using the `<cucumber.filter.tags>`, tags defined at Feature level Eg. `@users` will run all the scenarios in the [Users Feature](src/test/resources/Features/Users.feature) file in parallel. Likewise `@regression` will run all the scenarios from all the Feature files in parallel across multiple browsers/devices.
---	
### [Web application hosted on internal environment] Running your tests on BrowserStack using BrowserStackLocal

#### Prerequisites

- Clone the [BrowserStack demo application](https://github.com/browserstack/browserstack-demo-app) repository.
  ```sh
  git clone https://github.com/browserstack/browserstack-demo-app
  ``` 
- Please follow the README.md on the BrowserStack demo application repository to install and start the dev server on localhost.
- In this section, we will run a single test case to test the BrowserStack Demo app hosted on your local machine i.e. localhost. Refer to the [local.conf.json](src/test/resources/browserstack/conf/Run_Local_Test/local.conf.json) file to change test capabilities for this configuration.
- Note: You may need to provide additional BrowserStackLocal arguments to successfully connect your localhost environment with BrowserStack infrastructure. (e.g if you are behind firewalls, proxy or VPN).
- Further details for successfully creating a BrowserStackLocal connection can be found here:

  - [Local Testing with Automate](https://www.browserstack.com/local-testing/automate)
  - [BrowserStackLocal Java GitHub](https://github.com/browserstack/browserstack-local-java)


### [Web application hosted on internal environment] Run a specific test/entire test suite in parallel on a single BrowserStack browser/device using BrowserStackLocal

- How to run the test?

  - To run any test scenario (e.g. End to End Scenario) on a single BrowserStack browser using BrowserStackLocal, use the following command:
  
To point the tests on BrowserStack set the `<browser-type></browser-type>` property to `remote` in the Maven profile.

 Set the `<local></local>` property in the Maven profile to `True` to enable local connection.

Set the `<Application_url></Application_url>` properties in the pom.xml to your internal url i.e. `http://localhost:3000/`
	
  Maven:
  ```sh
  mvn test -P local-bs
  ```

You can mention any scenario from the feature files using the `<cucumber.filter.tags>`, tags defined at Feature level Eg. `@users` will run all the scenarios in the [Users Feature](src/test/resources/Features/Users.feature) file in parallel. Likewise `@regression` will run all the scenarios from all the Feature files in parallel.

- Output

  This run profile executes a single test/entire suite on an internally hosted web application on a single browser on BrowserStack in parallel. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.


### [Web application hosted on internal environment] Run the entire test suite in parallel on multiple BrowserStack browser using BrowserStackLocal

In this section, we will run the test cases to test the internally hosted website in parallel on multiple browsers/devices on Browserstack. Refer to the [local.parallel.json](src/test/resources/browserstack/conf/Run_Local_Parallel/local.parallel.json) file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on multiple devices and browsers using BrowserStackLocal, use the following command:

	To point the tests on BrowserStack set the `<browser-type></browser-type>` property to `remote` in the Maven profile.

	 Set the `<local></local>` property in the Maven profile to `True` to enable local connection.

	Set the `<Application_url></Application_url>` properties in the pom.xml to your internal url i.e. `http://localhost:3000/`
	
  Maven:
  ```sh
  mvn test -P local-cross-bs
  ```

- Output

  This run profile executes the entire test suite on an internally hosted web application on multiple browsers on BrowserStack. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.

- Note: By default, this execution would run maximum 5 test threads in parallel on BrowserStack. Thread count can be configured from [here](src/test/resources/browserstack/conf/Run_Local_Parallel/localParallel.testng.xml) based on your requirements.


## Additional Resources

- View your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
- Documentation for writing [Automate test scripts in Java](https://www.browserstack.com/automate/java)
- Customising your tests capabilities for Selenium 4 on BrowserStack using our [test capability generator](https://www.browserstack.com/automate/capabilities?tag=selenium-4)
- [List of Browsers & mobile devices](https://www.browserstack.com/list-of-browsers-and-platforms?product=automate) for automation testing on BrowserStack
- [Using Automate REST API](https://www.browserstack.com/automate/rest-api) to access information about your tests via the command-line interface
- Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)
- For testing public web applications behind IP restriction, [Inbound IP Whitelisting](https://www.browserstack.com/local-testing/inbound-ip-whitelisting) can be enabled with the [BrowserStack Enterprise](https://www.browserstack.com/enterprise) offering


## Open Issues
- The thread count can be updated directly from the testng runner file
- local tag is needed in the Maven profiles to enable Local Testing.
- The PDF report breaks for the multiple scenarios executed in parallel on multiple devices/browsers.
