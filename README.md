# java9-modularity

1. <b><i>The JAVA Platform module system:</b></i>

The defining feature for Java 9 is an all-new module system. When codebases grow larger, the odds of creating complicated, tangled “spaghetti code” increase exponentially. There are two fundamental problems: It is hard to truly encapsulate code, and there is no notion of explicit dependencies between different parts (JAR files) of a system. Every public class can be accessed by any other public class on the classpath, leading to inadvertent usage of classes that weren't meant to be public API. Furthermore, the classpath itself is problematic: How do you know whether all the required JARs are there, or if there are duplicate entries? The module system addresses both issues.

Modular JAR files contain an additional module descriptor. In this module descriptor, dependencies on other modules are expressed through`requires` statements. Additionally, `exports` statements control which packages are accessible to other modules. All non-exported packages are encapsulated in the module by default. Here's an example of a module descriptor, which lives in `module-info.java`:



2. <b><i>Linking</b></i>

When you have modules with explicit dependencies, and a modularized JDK, new possibilities arise. Your application modules now state their dependencies on other application modules and on the modules it uses from the JDK. Why not use that information to create a minimal runtime environment, containing just those modules necessary to run your application? That's made possible with the new jlink tool in Java 9. Instead of shipping your app with a fully loaded JDK installation, you can create a minimal runtime image optimized for your application.

3. <b><i>JShell: the interactive Java REPL (Read-Eval-Print-Loop)</b></i>

Many languages already feature an interactive Read-Eval-Print-Loop, and Java now joins this club. You can launch jshell from the console and directly start typing and executing Java code. The immediate feedback of jshell makes it a great tool to explore APIs and try out language features.

4.  <b><i>Improved Javadoc </b></i>
   Sometimes it's the little things that can make a big difference. Did you use Google all the time to find the right Javadoc pages, just like me? That's no longer necessary. Javadoc now includes search right in the API documentation itself. As an added bonus, the Javadoc output is now HTML5 compliant. Also, you'll notice that every Javadoc page includes information on which JDK module the class or interface comes from.


5. <b><i>Collection factory methods</b></i>


   Often you want to create a collection (e.g., a List or Set) in your code and directly populate it with some elements. That leads to repetitive code where you instantiate the collection, followed by several `add` calls. With Java 9, several so-called collection factory methods have been added:

    `Set<Integer> ints = Set.of(1, 2, 3);
    List<String> strings = List.of("first", "second");`
Besides being shorter and nicer to read, these methods also relieve you from having to pick a specific collection implementation. In fact, the collection implementations returned from the factory methods are highly optimized for the number of elements you put in. That's possible because they're immutable: adding items to these collections after creation results in an `UnsupportedOperationException`.

6. <b><i>Stream API improvements</b></i>


   The Streams API is arguably one of the best improvements to the Java standard library in a long time. It allows you to create declarative pipelines of transformations on collections. With Java 9, this only gets better. There are four new methods added to the Stream interface: dropWhile, takeWhile, ofNullable. The iterate method gets a new overload, allowing you to provide a Predicate on when to stop iterating:

`IntStream.iterate(1, i -> i < 100, i -> i + 1).forEach(System.out::println);`
The second argument is a lambda that returns true until the current element in the IntStream becomes 100. This simple example therefore prints the integers 1 until 99 on the console.

Besides these additions on Stream itself, the integration between Optional and Stream has been improved. It's now possible to turn an Optional object into a (possibly empty) Stream with the new `stream` method on Optional:

Stream<Integer> s = Optional.of(1).stream();
Turning an Optional into a Stream is especially useful when composing complex Stream pipelines.

7.<b><i> Private interface methods</b></i>


   Java 8 brought us default methods on interfaces. An interface can now also contain behavior instead of only method signatures. But what happens if you have several default methods on an interface with code that does almost the same thing? Normally, you'd refactor those methods to call a private method containing the shared functionality. But default methods can't be private. Creating another default method with the shared code is not a solution, because this helper method becomes part of the public API. With Java 9, you can add private helper methods to interfaces to solve this problem:

            public interface MyInterface {
            
                void normalInterfaceMethod();
            
                default void interfaceMethodWithDefault() {  init(); }
            
                default void anotherDefaultMethod() { init(); }
            
                // This method is not part of the public API exposed by MyInterface
                private void init() { System.out.println("Initializing"); }
            }
If you're evolving APIs with default methods, private interface methods can be helpful in structuring their implementation.

8. **_HTTP/2_**


   A new way of performing HTTP calls arrives with Java 9. This much overdue replacement for the old `HttpURLConnection` API also supports WebSockets and HTTP/2 out of the box. One caveat: The new HttpClient API is delivered as a so-called _incubator module_ in Java 9. This means the API isn't guaranteed to be 100% final yet. Still, with the arrival of Java 9 you can already start using this API:

HttpClient client = HttpClient.newHttpClient();

`HttpRequest req =
HttpRequest.newBuilder(URI.create("http://www.google.com"))
.header("User-Agent","Java")
.GET()
.build();
`

HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandler.asString());
Besides this simple request/response model, HttpClient provides new APIs to deal with HTTP/2 features such as streams and server push.

9. **_Multi-release JARs_**



      The last feature we're highlighting is especially good news for library maintainers. When a new version of Java comes out, it takes years for all users of your library to switch to this new version. That means the library has to be backward compatible with the oldest version of Java you want to support (e.g., Java 6 or 7 in many cases). That effectively means you won't get to use the new features of Java 9 in your library for a long time. Fortunately, the multi-release JAR feature allows you to create alternate versions of classes that are only used  when running the library on a specific Java version:

                                multirelease.jar
                                ├── META-INF
                                │   └── versions
                                │       └── 9
                                │           └── multirelease
                                │               └── Helper.class
                                ├── multirelease
                                ├── Helper.class
                                └── Main.class
In this case, multi release.jar can be used on Java 9, where instead of the top-level multi release.Helper class, the one under `META-INF/versions/9` is used. This Java 9-specific version of the class can use Java 9 features and libraries. At the same time, using this JAR on earlier Java versions still works, since the older Java versions only see the top-level Helper class.