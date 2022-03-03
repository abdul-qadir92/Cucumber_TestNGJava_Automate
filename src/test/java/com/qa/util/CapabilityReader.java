package com.qa.util;

import com.browserstack.local.Local;
import io.cucumber.java.Scenario;
import org.json.simple.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CapabilityReader {
    protected static HashMap<String, String> browserstackOptions = new HashMap<>();
    private static Local l;

    public static synchronized DesiredCapabilities getCapability(Map<String, String> envCapabilities, JSONObject config) throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getKey().toString().toLowerCase().contains("envoptions")) {
                browserstackOptions = (HashMap<String, String>) pair.getValue();
            } else
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
        }
        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            browserstackOptions.put(pair.getKey().toString(), pair.getValue().toString());
        }

        if (System.getenv("BROWSERSTACK_BUILD_NAME") != null) {
            browserstackOptions.put("buildName", System.getenv("BROWSERSTACK_BUILD_NAME").toString());
        }

        String username = System.getenv("BROWSERSTACK_USERNAME");
        if (username == null) username = (String) config.get("user");

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (accessKey == null) {
            accessKey = (String) config.get("key");
        }

        /*if (capabilities.getCapability("browserstack.local") != null
                && capabilities.getCapability("browserstack.local") == "true") {
            l = new Local();
            Map<String, String> options = new HashMap<String, String>();
            options.put("key", accessKey);
            l.start(options);
        }*/
        // Set the name of test to tags in maven
        if(System.getProperties().getProperty("cucumber.filter.tags")!=null){
            browserstackOptions.put("sessionName",System.getProperties().getProperty("cucumber.filter.tags"));
        }

        capabilities.setCapability("bstack:options", browserstackOptions);

        return capabilities;
    }
}
