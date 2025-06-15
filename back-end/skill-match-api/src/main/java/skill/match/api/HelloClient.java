package skill.match.api;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;
import io.micronaut.core.async.annotation.SingleResult;

@Client("/hello") // (1)
public interface HelloClient {

    @Get(consumes = MediaType.TEXT_PLAIN) // (2)
    @SingleResult
    Publisher<String> hello(); // (3)
}