package skill.match.api;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/hello")
public class HelloController {
    
    @Get(produces = MediaType.TEXT_PLAIN)
    public String hello(){
        return "Hello World";
    }
}
