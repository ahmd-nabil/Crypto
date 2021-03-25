package Cryptosystems;

import java.util.Locale;

public class PlayFair {
    public char[][] matrix = new char[5][5];
    public boolean[] found = new boolean[26]; // keeping in mind that when we update i (8) we update j (9)
    private final String key;
    public PlayFair(String k) {
        // first, clean the key >>> we remove spaces, convert key to all lowercase
        this.key = clean(k);

        int i = 0, j = 0;
        // second, Add all the characters of the Key first
        for(char c: key.toCharArray()) {
            if(j >= 5) {
                j %= 5;
                i++;
            }
            if(!found[c - 'a']) {
                matrix[i][j++] = c;
                found[c - 'a'] = true;
                if(c == 'i' || c == 'j') {
                    found[8] = true;
                    found[9] = true;
                }
            }
        }

        // third, Add alphabets (keeping uniqueness)
        for(int n=0; n<26; n++) {
            if(j >= 5) {
                j %= 5;
                i++;
            }
            if(!found[n]) {
                matrix[i][j++] = (char) (n + 'a');
                found[n] = true;
                if(n == 8 || n == 9) {
                    found[8] = true;
                    found[9] = true;
                }
            }
        }
        /** Creating matrix is DONE **/
    }

    public String encrypt(String plain) {
        plain = clean(plain);
        /** Clean the plain text
         * keep in mind 2 special cases
         * 1- if plain[i] == plain[i+1] && both in the same block append 'x' after i
         * 2- if i is even && plain[i] == length     append 'x' after i
         * */
        StringBuilder pt = new StringBuilder();
        for(int i=0; i<plain.length();) {
            char c1 = plain.charAt(i++);
            char c2 = (i == plain.length() || plain.charAt(i) == c1) ? 'x': plain.charAt(i++);
            pt.append(c1);
            pt.append(c2);
        }
        // Now we have pt Object in the right form to be encrypted
        StringBuilder ct = new StringBuilder();

        for(int i=0; i<pt.length(); i+=2) {
            // for each 2 characters
            // get their location in matrix
            // compare rows and columns to make the update
            // generate 2 new chars into ct

            int[] loc1 = findChar(pt.charAt(i));
            int[] loc2 = findChar(pt.charAt(i+1));

            /** We have 3 cases
             * 1- if Same Row i1 == i2, then, shift Right
             * 2- if Same Column j1 == j2, then, shift Down
             * 3- otherwise, swap columns
             * */

            char c1 , c2;
            if(loc1[0] == loc2[0]) { // Then SHIFT RIGHT (add 1 to columns)
                c1 = matrix[loc1[0]][(loc1[1] + 1) % 5];
                c2 = matrix[loc2[0]][(loc2[1] + 1) % 5];
            } else if (loc1[1] == loc2[1]) {
                c1 = matrix[(loc1[0] + 1) % 5][loc1[1]];
                c2 = matrix[(loc2[0] + 1) % 5][loc2[1]];
            } else {
                int tmp = loc1[1];
                loc1[1] = loc2[1];
                loc2[1] = tmp;
                if(loc1[0] <= loc2[0]) { // this is to make sure that the upper character gets picked first
                    c1 = matrix[loc1[0]][loc1[1]];
                    c2 = matrix[loc2[0]][loc2[1]];
                } else {
                    c1 = matrix[loc2[0]][loc2[1]];
                    c2 = matrix[loc1[0]][loc1[1]];
                }
            }
            ct.append(c1);
            ct.append(c2);
        }
        return ct.toString();
    }

    public String decrypt(String ct) {
        StringBuilder pt = new StringBuilder();
        for(int i=0; i<ct.length(); i+=2) {
            // for each 2 characters
            // get their location in matrix
            // compare rows and columns to make the update
            // generate 2 new chars into ct

            int[] loc1 = findChar(ct.charAt(i));
            int[] loc2 = findChar(ct.charAt(i+1));

            /** We have 3 cases
             * 1- if Same Row i1 == i2, then, shift Left
             * 2- if Same Column j1 == j2, then, shift UP
             * 3- otherwise, swap columns
             * */

            char c1 , c2;
            if(loc1[0] == loc2[0]) { // Then SHIFT LEFT (subtract 1 from columns)
                c1 = matrix[loc1[0]][mod(loc1[1] - 1, 5)];
                c2 = matrix[loc2[0]][mod(loc2[1] - 1, 5)];
            } else if (loc1[1] == loc2[1]) {    // Shift UP
                c1 = matrix[mod(loc1[0]-1, 5)][loc1[1]];
                c2 = matrix[mod(loc2[0] - 1, 5)][loc2[1]];
            } else {
                int tmp = loc1[1];
                loc1[1] = loc2[1];
                loc2[1] = tmp;
                if(loc1[0] <= loc2[0]) { // this is to make sure that the upper character gets picked first
                    c1 = matrix[loc2[0]][loc2[1]];
                    c2 = matrix[loc1[0]][loc1[1]];
                } else {
                    c1 = matrix[loc1[0]][loc1[1]];
                    c2 = matrix[loc2[0]][loc2[1]];
                }
            }
            pt.append(c1);
            pt.append(c2);
        }
        return pt.toString();
    }


    // UTILS
    private String clean(String s) {
        s = s.replaceAll(" ", "");
        s = s.toLowerCase(Locale.ROOT);
        return s;
    }

    private int[] findChar(char c) {
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                if(c == matrix[i][j])
                    return new int[]{i, j};
                // case of char 'i' and 'j'
                if((c == 'i' && matrix[i][j] == 'j') || (c == 'j' && matrix[i][j] == 'i'))
                    return new int[] {i, j};
            }
        }

        // not accessible, but needed for compiler
        return new int[]{-1, -1};
    }

    private int mod(int a, int b) { // to be able to make module of negative numbers
        return (a % b + b) % b;
    }
}
