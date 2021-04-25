# Custom Spring Security Authentication Filter

When you already have a custom authentication system and need to integrate it
with Spring Security, the most common way is using `AuthenticationManager`. If
it doesn't fit your needs, you can use a custom filter in the security filter 
chain.

This code sample shows how you can achieve that.

Read (and run) the tests to see how it works:

```bash
./mvnw test
```

There is a Quarkus version [here](../../quarkus/custom-authentication)

## You'll need

- Java 11+

## Useful links

- https://www.marcobehler.com/guides/spring-security