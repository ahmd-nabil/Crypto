package Cryptosystems;

import java.util.*;

public class Transposition {
    private final String key;

    public Transposition(String key) {
        this.key = key;
    }

    public String encrypt(String message) {
        LinkedHashMap<Character, List<Character>> map = new LinkedHashMap<>();
        for(char c: key.toCharArray()) {
            map.put(c, new ArrayList<>());
        }

        Iterator iterator = map.keySet().iterator();
        for(int i=0; i<message.length(); i++) {
            if(!iterator.hasNext())
                iterator = map.keySet().iterator();

            List<Character> list = map.get(iterator.next());
            list.add(message.charAt(i));
        }
        int i = 0;
        while(iterator.hasNext()) {
            List<Character> list = map.get(iterator.next());
            list.add((char) ('a' + i));
            i++;
        }

        StringBuilder cipher = new StringBuilder();
        List<Character> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);
        for(char k : keys) {
            List<Character> list = map.get(k);
            for (char c: list)
                cipher.append(c);
        }
        return cipher.toString();
    }

    public String decrypt(String cipher) {
        LinkedHashMap<Character, List<Character>> map = new LinkedHashMap<>();
        for(char k : key.toCharArray())
            map.put(k, new ArrayList<>());

        List<Character> keyChars = new ArrayList<>(map.keySet());
        Collections.sort(keyChars);
        int len = cipher.length() / key.length();
        int cur = 0;
        for(char k : keyChars) {
            List<Character> list = map.get(k);
            for(int i=0; i<len; i++)
                list.add(cipher.charAt(cur++));
        }

        StringBuilder plain = new StringBuilder();
        for(int row=0; row<len; row++) {
            for(char k : map.keySet()) {
                plain.append(map.get(k).get(row));
            }
        }
        return plain.toString();
    }
}
