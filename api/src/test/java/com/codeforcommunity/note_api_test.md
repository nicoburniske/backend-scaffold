# Testing the API

This is a mirror of the `main/` directory used for testing all of its source files. We use the [Vert.x Unit Testing Framework](https://vertx.io/docs/vertx-unit/java/#vertx_integration) to accomplish this. The naming convention we use is `...Test.java`

## Running Tests:

#### IMPORTANT: Before proceeding, please confirm that an instance of the API is running. Refer to the README in the root directory to learn how to accomplish this.

In another terminal tab, navigate to the root `api/` directory. In `api/`, if you haven't already, run `mvn clean install` to set up your dependencies. Run `mvn clean test` to see your tests in action.

## Writing Tests:
You will need to import several libraries from Vert.x core, Vert.x unit, and JUnit. Tests are placed into a public class by the same name as the file. 

Classes need to be annotated with `@RunWith(VertxUnitRunner.class)` for Vert.x to accept our tests. In a testing class, you need a method with a `@Before` annotation to initialize resources, the actual testing methods with a `@Test` annotation, and finally a method with an `@After` annotation to clean up our resources. You will most likely be receiving/sending JSON responses, so please read through the [documentation](https://vertx.io/docs/apidocs/io/vertx/core/json/JsonObject.html) for the `JsonObject` class if you get stuck when trying to parse certain data types.

An example can be found in `rest/ApiRouterTest.java`. For a complete reference, here's some [boilerplate code](https://github.com/vert-x3/vertx-examples/blob/master/unit-examples/src/test/java/io/vertx/example/unit/test/MyJUnitTest.java). 

*If you have more questions, please reach out to Brandon Liang on the Testing/Security Team.*