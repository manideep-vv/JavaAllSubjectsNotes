package examples.users;

import com.intuit.karate.junit5.Karate;

class ExamplesTest {
    
    // this will run all *.feature files that exist in sub-directories
    // see https://github.com/intuit/karate#naming-conventions   
//    @Karate.Test
        public   Karate testAll() {
        return Karate.run().relativeTo(getClass());
    }
    @Karate.Test
    public   Karate testSpecificTags() {
        return Karate.run().tags("@sai").relativeTo(getClass());
    }
}
