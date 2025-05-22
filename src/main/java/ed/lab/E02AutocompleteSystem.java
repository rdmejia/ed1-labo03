package ed.lab;

import java.util.*;

public class E02AutocompleteSystem {
    // Mapa para guardar cada oración y su frecuencia
    private final Map<String, Integer> counts;
    // Prefijo actual que se está construyendo
    private final StringBuilder prefix;

    /**
     * Inicializa el sistema con las oraciones y frecuencias dadas.
     */
    public E02AutocompleteSystem(String[] sentences, int[] times) {
        counts = new HashMap<>();
        prefix = new StringBuilder();
        for (int i = 0; i < sentences.length; i++) {
            counts.put(sentences[i], counts.getOrDefault(sentences[i], 0) + times[i]);
        }
    }

    /**
     * Procesa el próximo carácter ingresado.
     * @param c carácter entrante (letra, espacio o '#')
     * @return hasta tres sugerencias ordenadas por frecuencia y luego lexicográficamente
     */
    public List<String> input(char c) {
        // Si es '#', finalizamos la oración actual y la almacenamos
        if (c == '#') {
            String sentence = prefix.toString();
            counts.put(sentence, counts.getOrDefault(sentence, 0) + 1);
            prefix.setLength(0);
            return Collections.emptyList();
        }

        // Añadimos el carácter al prefijo
        prefix.append(c);
        String p = prefix.toString();

        // Recolectamos todas las oraciones que inician con el prefijo
        List<String> candidates = new ArrayList<>();
        for (String s : counts.keySet()) {
            if (s.startsWith(p)) {
                candidates.add(s);
            }
        }

        // Ordenamos por frecuencia descendente y luego lexicográficamente
        candidates.sort((a, b) -> {
            int fa = counts.get(a), fb = counts.get(b);
            if (fa != fb) return fb - fa;
            return a.compareTo(b);
        });

        // Devolvemos hasta las 3 primeras sugerencias
        return candidates.size() > 3 ? candidates.subList(0, 3) : candidates;
    }
}
