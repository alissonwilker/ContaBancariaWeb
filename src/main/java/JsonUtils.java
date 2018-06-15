public class JsonUtils {
    static class CampoJson {
        private String chave;
        private Object valor;
        
        public CampoJson(String chave, Object valor) {
            this.chave = chave;
            this.valor = valor;
        }

        @Override
        public String toString() {
            String valorStr = valor != null ? valor.toString() : ""; 
            return "\"" + chave + "\":\"" + valorStr + "\"";
        }
    }
    
    public static String criarJsonObject(CampoJson... camposJson) {
        StringBuffer resposta = new StringBuffer();
        resposta.append("{ ");
        
        for (int i = 0; i < camposJson.length; i++) {
            if (i != 0) {
                resposta.append(", ");
            }
            resposta.append(camposJson[i].toString());
        }
        
        resposta.append(" }");
        
        return resposta.toString();
    }
}
