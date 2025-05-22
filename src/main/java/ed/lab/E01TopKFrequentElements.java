package ed.lab;

import java.util.*;

public class E01TopKFrequentElements {
    public int[] topKFrequent(int[] nums, int k) {
        // 1. Contar frecuencia de cada número
        Map<Integer, Integer> freq = new HashMap<>();
        for (int n : nums) {
            freq.put(n, freq.getOrDefault(n, 0) + 1);
        }

        // 2. Bucket sort por frecuencia: índice = frecuencia, valor = lista de números con esa frecuencia
        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new List[nums.length + 1];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            int number = entry.getKey();
            int count = entry.getValue();
            buckets[count].add(number);
        }

        // 3. Recoger los k elementos más frecuentes, desde la frecuencia más alta hacia abajo
        int[] result = new int[k];
        int idx = 0;
        for (int f = buckets.length - 1; f >= 0 && idx < k; f--) {
            for (int num : buckets[f]) {
                result[idx++] = num;
                if (idx == k) break;
            }
        }
        return result;
    }
}

