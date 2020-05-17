# The quirks of Spring WebClient and ```List<String>```

As of Spring Core 5.0 the RestTemplate is in maintenance mode and it is
suggested to use the new [WebClient](https://docs.spring.io/spring/docs/5.2.6.RELEASE/spring-framework-reference/web.html#webmvc-resttemplate).

The new WebClient has a nice API and due to its reactive support (sync, async, stream)
feels quite future-proof.

It's all nice and shiny. Until you try to read a ```List<String>``` from a rest endpoint.

If you are not very careful the client will not throw an exception, but it will also
not return the list of string, rather you will end up with a list containing only
one entry or more (depending on the newlines in the rest endpoint response).

There are some related questions on Stackoverflow and reported issues
in the issue tracker:

* [Why is Flux.fromIterable() return in RestController coming back as one concatenated string?](https://stackoverflow.com/questions/54856858/why-is-flux-fromiterable-return-in-restcontroller-coming-back-as-one-concatena/54858760)
* [Deserialize a json array to objects using Jackson and WebClient](https://stackoverflow.com/questions/48598233/deserialize-a-json-array-to-objects-using-jackson-and-webclient)
* [13020](https://github.com/spring-projects/spring-boot/issues/13020)
* [20807](https://github.com/spring-projects/spring-framework/issues/20807)
* [22662](https://github.com/spring-projects/spring-framework/issues/22662)

As you read through the issue tracker there is good news. It will work! But
not the way you think it will. The behaviour of WebClient to treat ```List<String>```
differently as everything else is intentional. Read about this in the Spring Docs [Jackson JSON](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#webflux-codecs-jackson)
and about [line-delimited JSON](https://en.wikipedia.org/wiki/JSON_streaming).

This repository shows variations of using WebClient to be able to read a ```List<String>```
from a rest endpoint without sacrificing type support or too ugly code.

NOTE: using ```.block()``` is only allowed when you know what you are doing.
