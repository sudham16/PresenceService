import com.google.gson.Gson;
import model.InBody;
import model.Input;
import model.Stats;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Results {

    public static void getResults(String payload) throws IOException {
        System.out.println(payload);
        List<String> content = new ArrayList<>();
        Path path = Paths.get("D://output.txt");
        String[] lines = payload.split("\\r?\\n");
        HashMap<String, String> mapStore = new HashMap<>();
        for(String line : lines ) {
            Gson gson = new Gson();
            Input input = gson.fromJson(line, Input.class);
            System.out.println(input.getBody());
            InBody inBody = gson.fromJson(input.getBody(), InBody.class);
             System.out.println(inBody.getStats());
            for (Stats st : inBody.getStats()) {
                String value = st.getValue();
                if (value.matches("\\d+:\\d+:\\d+")) {
                    String[] values = value.split(":");
                    int[] hhmmss = Arrays.stream(values).mapToInt(Integer::parseInt).toArray();
                    int i = (hhmmss[0] * 60) + hhmmss[1];
                    value = String.valueOf(i);
                }
                if (content.isEmpty()) {
                    mapStore.put(inBody.getTarget(), inBody.getTarget());
                    content.add("eccssre_cr{kpi=\"vq_stats\", errl=\"false\", vq=\"" + inBody.getTarget() + "\", environment=\"PROD\", stat_id=\"" + st.getName() + "\"} " + value);
                } else {
                    if (mapStore.get(inBody.getTarget()) == null) {
                        mapStore.put(inBody.getTarget(), inBody.getTarget());
                        content.add("\n");
                    }
                    content.add("eccssre_cr{kpi=\"vq_stats\", errl=\"false\", vq=\"" + inBody.getTarget() + "\", environment=\"PROD\", stat_id=\"" + st.getName() + "\"} " + value);

                }
            }
        }
        Files.write(path, content, StandardCharsets.UTF_8);
    }
}
