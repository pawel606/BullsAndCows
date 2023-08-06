import java.util.Random;
import java.util.Scanner;

public class Main {
    public static boolean Check(String secret) { //check answer(bulls and cows)
        Scanner sc = new Scanner(System.in);
        String number = sc.nextLine();
        int bull = 0;
        int cow = 0;

        for (int i = 0; i < secret.length(); i++) {
            char tmp = number.charAt(i);
            if (secret.charAt(i) == number.charAt(i)) {
                bull++;
            } else {
                for (int j = 0; j < secret.length(); j++) {
                    if (secret.charAt(j) == number.charAt(i)) {
                        cow++;
                        break;
                    }
                }
            }
        }
        if (bull == 0 && cow == 0) {
            System.out.println("Grade: None");
        } else if (bull == 0) {
            if(cow == 1) {
                System.out.println("Grade: " + cow + " cow");
            }else {
                System.out.println("Grade: " + cow + " cows");
            }
        } else if (cow == 0) {
            if(bull == 1) {
                System.out.println("Grade: " + bull + " bull");
            }else {
                System.out.println("Grade: " + bull + " bulls");
            }
        } else {
            if(bull == 1 && cow == 1) {
                System.out.println("Grade: " + bull + " bull and " + cow + " cow");
            }else if(bull == 1) {
                System.out.println("Grade: " + bull + " bull and " + cow + " cows");
            }else if(cow == 1) {
                System.out.println("Grade: " + bull + " bulls and " + cow + " cow");
            } else {
                System.out.println("Grade: " + bull + " bull(s) and " + cow + " cow(s)");
            }
        }
        return bull == secret.length();
    }

    public static String secretNumber(int length, int possibilities) { //generate number and return number as String
        StringBuilder secret = new StringBuilder();
        boolean correctNumber = true;
        Random random = new Random();
        int upper = 122; //ASCII code for 'z'
        int upperDigits = 57; //ASCII code for '9'
        int lower = 48; //ASCII code for '0'
        int tmp = 0;
        int numbersBlocked = 10 - possibilities; //possibilities of random number
        int lettersBlocked = 26 - (possibilities - 10); //possibilities for random number and letter
        boolean moreThan10 = possibilities > 10;

        if(possibilities > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return null;
        }

        if (length >= 1 && length <= 36) {
            if (possibilities >= length) {
                while (correctNumber) {
                    if (possibilities <= 10) {
                        tmp = random.nextInt(upperDigits - lower - numbersBlocked + 1) + lower; //generate random digit if possibilities <= 10
                    } else {
                        tmp = random.nextInt(upper - lower - lettersBlocked + 1) + lower; //generate random digit and letter if possibilities > 10
                    }
                    if (tmp >= 58 && tmp <= 96) {
                        continue;
                    }
                    char rand = (char) tmp;
                    if (secret.length() == 0) {
                        secret.append(rand);
                    } else if (secret.length() > 0 && secret.length() < length) {
                        boolean canBeAppend = true;
                        for (int i = 0; i < secret.length(); i++) { //checking if random appears in Secret
                            if (secret.charAt(i) == rand) {
                                canBeAppend = false;
                            }
                        }
                        if (canBeAppend) {
                            secret.append(rand);
                        }
                    }
                    if (secret.length() == length) {
                        correctNumber = false;
                    }

                }
                StringBuilder info = new StringBuilder("The secret is prepared: "); //creating a string to show info about encrypting word
                info.append("*".repeat(Math.max(0, secret.length())));
                if (moreThan10) {
                    if (possibilities == 11) {
                        info.append(" (0-9, a).");
                    } else {
                        info.append(" (0-9, a-");
                        info.append((char) (upper - lower - lettersBlocked + lower)).append(").");
                    }
                } else {
                    if (possibilities == 1) {
                        info.append(" (0).");
                    } else {
                        info.append(" (0-");
                        info.append((char) (upperDigits - lower - numbersBlocked + lower)).append(").");
                    }
                }

                System.out.println(info);
                return secret.toString();
            }else {
                System.out.println("Error: it's not possible to generate a code with a length of " + length + " with " + possibilities +" unique symbols.");
                return null;
            }
        }
        System.out.println("Error: can't generate a secret number with a length of "+ length +" because there aren't enough unique digits.");
        return null;
    }

    public static void game() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        String lengthS = sc.nextLine();

        try { //checking if input is a number
            int length = Integer.parseInt(lengthS);
            System.out.println("Input the number of possible symbols in the code:");
            String possibilitiesS = sc.nextLine();
            try { //checking if second input is a number
                int possibilities = Integer.parseInt(possibilitiesS);
                String secret = secretNumber(length, possibilities);
                if (secret != null) {
                    System.out.println("Okay, let's start a game!");
                    boolean endGame = false;
                    int round = 1;
                    while (!endGame) {
                        System.out.println("Turn " + round + ":");
                        endGame = Check(secret);
                        round++;
                    }
                    System.out.println("Congratulations! You guessed the secret code.");
                }
            }catch (Exception e) {
                System.out.println("Error \"" + possibilitiesS + "\" isn't a valid number.");
            }
        }catch (Exception e) {
            System.out.println("Error \"" + lengthS + "\" isn't a valid number.");
        }
    }

    public static void main (String[]args){
        game();
    }
}
