package com.service;

import com.google.gson.Gson;
import com.model.InBody;
import com.model.Input;
import com.model.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class ResultFormatter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultFormatter.class);

    public static void writeAndFormatResults(String payload, String outputDir) {
        if (payload.contains("header")) {
            List<String> content = new ArrayList<>();
            Path path = Paths.get(outputDir);
            Gson gson = new Gson();
            Input input = gson.fromJson(payload, Input.class);
            LOGGER.debug(input.getBody());
            InBody inBody = gson.fromJson(input.getBody(), InBody.class);
            for (Stats st : inBody.getStats()) {
                String value = st.getValue();
                if (value.matches("\\d+:\\d+:\\d+")) {
                    String[] values = value.split(":");
                    int[] hhmmss = Arrays.stream(values).mapToInt(Integer::parseInt).toArray();
                    int i = (hhmmss[0] * 60) + hhmmss[1];
                    value = String.valueOf(i);
                }
                content.add("eccssre_cr{kpi=\"vq_stats\", errl=\"false\", vq=\"" + inBody.getTarget() + "\", environment=\"PROD\", stat_id=\"" + st.getName() + "\"} " + value);
            }
            content.add("\n");
            try {
                Files.write(path, content, StandardCharsets.UTF_8,CREATE,APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
