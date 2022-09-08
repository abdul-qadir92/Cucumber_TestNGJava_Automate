package com.qa.util;

import Runner.CucumberTest;
import com.browserstack.local.Local;
import org.json.simple.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        // Set the build name for tests
        //SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YY hh.mm");
        //browserstackOptions.put("buildName",browserstackOptions.get("projectName")+"-"+sdf.format(new Date()));
        browserstackOptions.put("buildName",CucumberTest.buildname);
        if (System.getenv("BROWSERSTACK_BUILD_NAME") != null) {
            browserstackOptions.put("buildName", System.getenv("BROWSERSTACK_BUILD_NAME").toString());
        }

        capabilities.setCapability("bstack:options", browserstackOptions);

        return capabilities;
    }
}
