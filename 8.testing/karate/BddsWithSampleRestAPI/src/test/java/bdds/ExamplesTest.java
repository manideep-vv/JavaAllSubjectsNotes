package bdds;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.Test;
import com.intuit.karate.Runner;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

class ExamplesTest {
    
    // this will run all *.feature files that exist in sub-directories
    // see https://github.com/intuit/karate#naming-conventions   
//    @Karate.Test
        public   Karate testAll() {
        return Karate.run().relativeTo(getClass());
    }
    @Karate.Test
    public   Karate testSpecificTags() {
//        Map<String, Object> args = new HashMap();
//        args.put("name", "World");

//        Runner.runFeature("classpath:bdds/empCrudv1.feature", args, true);
        Karate dynamicNodes = Karate.run().tags("@mani").outputCucumberJson(true).relativeTo(getClass());
        return dynamicNodes;
    }

    @Test
    public void testInParallel(){
        System.out.println("running test  case parallelly ");
        //way -1 using karate.run()
        Results parallelResults = Karate.run().tags("@mani").relativeTo(getClass()).outputCucumberJson(true).parallel(3);
        //way-2 using Runner.path
//                Results results = Runner.path("classpath:bdds/")
//                .tags("@mani")
//                .outputCucumberJson(true)
//                .parallel(3);
        generateReport(parallelResults.getReportDir());
        assertTrue(parallelResults.getFailCount()==0);
    }
    public static void generateReport(String karateOutputPath) {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[] {"json"}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("target"), "BddsWithSampleRestAPI");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }
}
