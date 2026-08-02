package com.auditsphere.auditspherebackend.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;



@Service
public class GeminiService {


    @Value("${gemini.api.key}")
    private String apiKey;



    private final WebClient webClient;



    public GeminiService(WebClient.Builder builder) {

        this.webClient = builder.build();

    }





    public String generateInsight(String prompt) {


        try {


            String url =

                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="
                            + apiKey;





            String body = """

                    {
                      "contents": [
                        {
                          "parts": [
                            {
                              "text": "%s"
                            }
                          ]
                        }
                      ]
                    }

                    """.formatted(
                    prompt.replace("\"", "\\\"")
            );







            String response = webClient.post()

                    .uri(url)

                    .header(
                            "Content-Type",
                            "application/json"
                    )

                    .bodyValue(body)

                    .retrieve()

                    .bodyToMono(String.class)

                    .block();






            System.out.println("========== GEMINI RESPONSE ==========");

            System.out.println(response);

            System.out.println("=====================================");







            ObjectMapper mapper = new ObjectMapper();


            JsonNode root =
                    mapper.readTree(response);






            // Check Gemini error response

            if(root.has("error")){


                return "Gemini Error : "
                        +
                        root
                                .path("error")
                                .path("message")
                                .asText();

            }







            JsonNode textNode =

                    root

                            .path("candidates")

                            .get(0)

                            .path("content")

                            .path("parts")

                            .get(0)

                            .path("text");








            if(textNode.isMissingNode()){


                return "No AI insight generated.";

            }






            return textNode.asText();






        }
        catch (Exception e) {

            e.printStackTrace();

            return """
AI Insights Temporarily Unavailable

The external AI service is currently unavailable or has reached its request limit.

AuditSphere is functioning normally.
Please try refreshing later after the AI service quota resets.
""";

        }


    }


}