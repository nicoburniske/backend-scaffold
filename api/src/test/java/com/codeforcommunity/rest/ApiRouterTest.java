package com.codeforcommunity.test;

import com.codeforcommunity.rest.ApiRouter;

import java.lang.ProcessBuilder;
import java.io.File;
import java.util.Arrays;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;

import io.vertx.ext.unit.TestSuite;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class ApiRouterTest {
  Vertx vertx;
  HttpServer server;
  // ProcessBuilder pb;
  // Process p;

  @Before
  // Do some initialization "before" we start running tests
  public void before(TestContext context) /* throws java.io.IOException */ {
    vertx = Vertx.vertx();
    /*
    System.out.println("Working Directory = " + System.getProperty("user.dir"));
    pb = new ProcessBuilder("java", "-jar", "../service/target/service-1.0-SNAPSHOT-jar-with-dependencies.jar");
    pb.directory(new File("../service/target/"));
    p = pb.start();
    */
  }

  @After
  // Tear down resources used by vertx testing
  public void after(TestContext context) /* throws java.io.IOException */ {
    // p.destroy();
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  // Just as a sanity check with Vert.x unit testing
  public void testSomething(TestContext context) {
    context.assertFalse(false);
  }

  @Test
  // Are all the keys we want are returned in the JSON response?
  public void emptyTestParameters(TestContext context) {
    HttpClient client = vertx.createHttpClient();
    Async async = context.async();

    client.getNow(8081, "localhost", "/api/v1/notes", resp -> {
      resp.bodyHandler(body -> {
        JsonObject b = new JsonObject(body);
        context.assertEquals(resp.statusCode(), 200);

        context.assertEquals(b.containsKey("status"), true);
        context.assertEquals(b.containsKey("notes"), true);
        context.assertEquals(b.containsKey("not-here"), false);

        client.close();
        async.complete();
      });
    });
  }

  @Test
  // Are all the definitions we want are returned in the JSON response?
  public void emptyTest(TestContext context) {
    HttpClient client = vertx.createHttpClient();
    Async async = context.async();

    client.getNow(8081, "localhost", "/api/v1/notes", resp -> {
      resp.bodyHandler(body -> {
        JsonObject b = new JsonObject(body);

        context.assertEquals(b.getString("status"), "OK");
        context.assertEquals(b.getJsonArray("notes").size(), 0);

        client.close();
        async.complete();
      });
    });
  }
}