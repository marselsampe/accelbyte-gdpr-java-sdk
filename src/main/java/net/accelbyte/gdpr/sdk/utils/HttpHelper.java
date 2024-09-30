/*
 * Copyright (c) 2023 AccelByte Inc. All Rights Reserved
 * This program is made available under the terms of the MIT License.
 */

package net.accelbyte.gdpr.sdk.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

@Slf4j
public class HttpHelper {

    public static String uploadFile(String uploadUrl, byte[] data) throws IOException {
        HttpClient httpClient = HttpClients.custom().build();
        HttpPut put = new HttpPut(uploadUrl);
        HttpEntity entity = EntityBuilder.create()
                .setBinary(data)
                .build();
        put.setEntity(entity);
        put.setHeader("Content-Type","application/zip");

        HttpResponse response = httpClient.execute(put);
        int responseCode = response.getStatusLine().getStatusCode();
        if (responseCode == 200) {
            return "";
        } else {
            String responseBody = response.getEntity() != null ? EntityUtils.toString(response.getEntity()): "";
            return String.format("Failed upload file to uploadUrl [%s], Response code [%d], Response body: %s",
                    uploadUrl, responseCode, responseBody.replaceAll("[\n\r\t]", ""));
        }
    }
}
