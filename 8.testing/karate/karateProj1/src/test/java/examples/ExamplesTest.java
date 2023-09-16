package examples;

import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;

import java.util.HashMap;
import java.util.Map;

class ExamplesTest {
    
    // this will run all *.feature files that exist in sub-directories
    // see https://github.com/intuit/karate#naming-conventions   
    @Karate.Test
    Karate testAll() {
        Map<String, Object> args = new HashMap();
        args.put("name", "World");

        Runner.runFeature("classpath:examples/users/users.feature", args, true);
        return Karate.run().relativeTo(getClass());
    }
    
}
