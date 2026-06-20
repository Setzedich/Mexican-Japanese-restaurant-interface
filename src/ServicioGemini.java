import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServicioGemini {

    private String apiKey;
    private String modelo;
    private String urlApi;

    public ServicioGemini() {
        apiKey = ConfiguracionGemini.API_KEY;
        modelo = "gemini-2.5-flash";
        urlApi = "https://generativelanguage.googleapis.com/v1beta/models/" + modelo + ":generateContent";
    }

    public String pedirRecomendacion(String menuTexto, String carritoTexto) {
        String prompt = crearPromptRestaurante(menuTexto, carritoTexto);
        return enviarPrompt(prompt);
    }

    public String responderPreguntaCliente(String pregunta, String menuTexto) {
        String prompt = "";

        prompt = prompt + "Eres un asistente amable de un restaurante México-Japonés.\n";
        prompt = prompt + "Tu trabajo es ayudar al cliente con dudas sobre el menú.\n\n";

        prompt = prompt + "Reglas:\n";
        prompt = prompt + "1. Usa solo productos que aparezcan en el menú.\n";
        prompt = prompt + "2. No inventes productos, precios ni promociones.\n";
        prompt = prompt + "3. Responde claro, corto y amable.\n";
        prompt = prompt + "4. Si no tienes información suficiente, dilo de forma educada.\n\n";

        prompt = prompt + "Menú disponible:\n";
        prompt = prompt + menuTexto + "\n\n";

        prompt = prompt + "Pregunta del cliente:\n";
        prompt = prompt + pregunta + "\n\n";

        prompt = prompt + "Responde en máximo 5 líneas.";

        return enviarPrompt(prompt);
    }

    private String crearPromptRestaurante(String menuTexto, String carritoTexto) {
        String prompt = "";

        prompt = prompt + "Eres un asistente inteligente de un restaurante México-Japonés.\n";
        prompt = prompt + "Tu trabajo es recomendar combinaciones de comida, bebida y postre.\n";
        prompt = prompt + "Usa un estilo amable, claro y breve.\n\n";

        prompt = prompt + "Reglas obligatorias:\n";
        prompt = prompt + "1. No inventes productos.\n";
        prompt = prompt + "2. Usa solamente productos que aparezcan en el menú disponible.\n";
        prompt = prompt + "3. Recomienda máximo 2 productos.\n";
        prompt = prompt + "4. Explica la razón de forma sencilla.\n";
        prompt = prompt + "5. No hagas respuestas largas.\n";
        prompt = prompt + "6. Si el carrito está vacío, recomienda una combinación inicial.\n";
        prompt = prompt + "7. Si ya hay productos en el carrito, recomienda algo que combine con ellos.\n\n";

        prompt = prompt + "Menú disponible:\n";
        prompt = prompt + menuTexto + "\n\n";

        prompt = prompt + "Carrito actual del cliente:\n";
        prompt = prompt + carritoTexto + "\n\n";

        prompt = prompt + "Responde exactamente con este formato:\n";
        prompt = prompt + "Recomendación:\n";
        prompt = prompt + "Motivo:\n";
        prompt = prompt + "Producto sugerido:\n";

        return prompt;
    }

    public String enviarPrompt(String prompt) {
        String respuestaFinal = "";

        try {
            URL url = new URL(urlApi);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("x-goog-api-key", apiKey);
            conexion.setDoOutput(true);

            String json = crearJson(prompt);

            OutputStream salida = conexion.getOutputStream();
            salida.write(json.getBytes("UTF-8"));
            salida.close();

            int codigoRespuesta = conexion.getResponseCode();

            BufferedReader lector;

            if (codigoRespuesta >= 200 && codigoRespuesta < 300) {
                lector = new BufferedReader(new InputStreamReader(conexion.getInputStream(), "UTF-8"));
            } else {
                lector = new BufferedReader(new InputStreamReader(conexion.getErrorStream(), "UTF-8"));
            }

            String linea;
            StringBuilder respuesta = new StringBuilder();

            while ((linea = lector.readLine()) != null) {
                respuesta.append(linea);
            }

            lector.close();
            conexion.disconnect();

            if (codigoRespuesta >= 200 && codigoRespuesta < 300) {
                respuestaFinal = extraerTextoRespuesta(respuesta.toString());
            } else {
                respuestaFinal = "Error al conectar con Gemini.\nCódigo: " + codigoRespuesta + "\n" + respuesta.toString();
            }

        } catch (Exception e) {
            respuestaFinal = "No se pudo conectar con la API de Gemini.\n" + e.getMessage();
        }

        return respuestaFinal;
    }

    private String crearJson(String prompt) {
        String promptSeguro = escaparTextoJson(prompt);

        String json = "";

        json = json + "{";
        json = json + "\"contents\":[";
        json = json + "{";
        json = json + "\"parts\":[";
        json = json + "{";
        json = json + "\"text\":\"" + promptSeguro + "\"";
        json = json + "}";
        json = json + "]";
        json = json + "}";
        json = json + "],";
        json = json + "\"generationConfig\":{";
        json = json + "\"temperature\":0.3,";
        json = json + "\"topP\":0.8,";
        json = json + "\"topK\":40,";
        json = json + "\"maxOutputTokens\":250";
        json = json + "}";
        json = json + "}";

        return json;
    }

    private String escaparTextoJson(String texto) {
        String textoSeguro = texto;

        textoSeguro = textoSeguro.replace("\\", "\\\\");
        textoSeguro = textoSeguro.replace("\"", "\\\"");
        textoSeguro = textoSeguro.replace("\n", "\\n");
        textoSeguro = textoSeguro.replace("\r", "");

        return textoSeguro;
    }

    private String extraerTextoRespuesta(String json) {
        String texto = "";
        String buscar = "\"text\":";
        int posicion = json.indexOf(buscar);

        if (posicion == -1) {
            return "Gemini respondió, pero no se encontró texto en la respuesta.";
        }

        int inicio = json.indexOf("\"", posicion + buscar.length());

        if (inicio == -1) {
            return "Gemini respondió, pero no se pudo leer el texto.";
        }

        inicio = inicio + 1;

        int fin = inicio;
        boolean encontrado = false;

        while (fin < json.length() && encontrado == false) {
            char caracter = json.charAt(fin);

            if (caracter == '"' && json.charAt(fin - 1) != '\\') {
                encontrado = true;
            } else {
                fin++;
            }
        }

        if (fin <= inicio || fin > json.length()) {
            return "Gemini respondió, pero el texto llegó vacío.";
        }

        texto = json.substring(inicio, fin);
        texto = limpiarTextoRespuesta(texto);

        return texto;
    }

    private String limpiarTextoRespuesta(String texto) {
        String limpio = texto;

        limpio = limpio.replace("\\n", "\n");
        limpio = limpio.replace("\\\"", "\"");
        limpio = limpio.replace("\\\\", "\\");
        limpio = limpio.replace("\\u00e1", "á");
        limpio = limpio.replace("\\u00e9", "é");
        limpio = limpio.replace("\\u00ed", "í");
        limpio = limpio.replace("\\u00f3", "ó");
        limpio = limpio.replace("\\u00fa", "ú");
        limpio = limpio.replace("\\u00c1", "Á");
        limpio = limpio.replace("\\u00c9", "É");
        limpio = limpio.replace("\\u00cd", "Í");
        limpio = limpio.replace("\\u00d3", "Ó");
        limpio = limpio.replace("\\u00da", "Ú");
        limpio = limpio.replace("\\u00f1", "ñ");
        limpio = limpio.replace("\\u00d1", "Ñ");

        return limpio;
    }
    
    public String preguntar(String pregunta) {
        String prompt = "";

        prompt = prompt + "Eres Aiko, el asistente virtual de un restaurante México-Japonés.\n";
        prompt = prompt + "Responde de forma amable, clara y breve.\n\n";

        prompt = prompt + "Reglas:\n";
        prompt = prompt + "1. Ayuda al cliente con dudas sobre menú, pedidos, promociones y recomendaciones.\n";
        prompt = prompt + "2. No inventes información muy específica si no la conoces.\n";
        prompt = prompt + "3. Si no sabes algo, responde de forma educada.\n";
        prompt = prompt + "4. Responde en español.\n";
        prompt = prompt + "5. No hagas respuestas demasiado largas.\n\n";

        prompt = prompt + "Pregunta del cliente:\n";
        prompt = prompt + pregunta + "\n\n";

        prompt = prompt + "Respuesta:";

        return enviarPrompt(prompt);
    }
}