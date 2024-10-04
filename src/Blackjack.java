import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Blackjack {
    private int rate, balance, score, croupierScore,
    newScore, newCroupierScore;
    private String choise, fileBalance, strBalance, choise2;
    private int min = 2, max = 11;
    private Random randomScore = new Random();
    private String fileNameBal = "путь к Blackjack in Java\\res\\balance.txt";
    private String fileNameIns = "путь к Blackjack in Java\\res\\instilled.txt";
    private String fileNameInfoWin = "путь к Blackjack in Java\\res\\infoWin.txt";
    private String fileNameInfoLosing = "путь к Blackjack in Java\\res\\infoLosing.txt";
    private String fileNameInfoGames = "путь к Blackjack in Java\\res\\infoGames.txt";
    private Scanner line = new Scanner(System.in);
    private Scanner sc2 = new Scanner(System.in);
    private Scanner sc3 = new Scanner(System.in);
    private File fileInfoLosing = new File(fileNameInfoLosing);
    private File fileInfoGames = new File(fileNameInfoGames);
    private File fileInfoWin = new File(fileNameInfoWin);
    private File file = new File(fileNameBal);
    private int numGames;
    private int numLosing;
    private int numWin;

    private void run() throws IOException {

        try (Scanner scFileBalance = new Scanner(new File(fileNameBal))) {
            while (scFileBalance.hasNext())
                strBalance = scFileBalance.nextLine().toString();

            balance = Integer.parseInt(strBalance);
            if (balance == 0 || balance < 0) {
                balance += 100;
            }
            System.out.print("\nБаланс: " + balance + ". Ставка: ");
        }

        rate = line.nextInt();
        if (rate < 0) {
            System.out.print("Вы не можете сделать ставку меньше 0!\nВведите еще раз: ");
            rate = line.nextInt();
        } else if (rate > balance) {
            System.out.print("Вы не можете сделать ставку больше своего баланса!\nВведите еще раз: ");
            rate = line.nextInt();
        }
        score = randomScore.nextInt(max - min + 1) + min;
        croupierScore = randomScore.nextInt(max - min + 1) + min;

        System.out.println("Ваш счет:" + score);
        System.out.println("Счет крупье:" + croupierScore);

        while (true) {
            try (Scanner scanner = new Scanner(new File(fileNameInfoLosing))){
                numLosing = Integer.parseInt(scanner.nextLine().toString());
            }
            try (Scanner scanner = new Scanner(new File(fileNameInfoWin))){
                numWin = Integer.parseInt(scanner.nextLine().toString());
            }

            System.out.print("Взять(1). Хватит(2). Ввод: ");
            choise = sc2.nextLine();
            if (choise.equals("1")) {

                newScore = randomScore.nextInt(max - min + 1) + min;
                newCroupierScore = randomScore.nextInt(max - min + 1) + min;

                croupierScore += newCroupierScore;
                score += newScore;

                if (score > 21 || croupierScore == 21) {
                    System.out.println("К сожелению вы проиграли\n" + "Счет: " + score + ". Счет крупье: " + croupierScore);
                    balance -= rate;

                    if (!file.exists()) {
                        System.out.println("Файл не найден или не существует.");
                        return;
                    }
                    // Перезапись файла пустой строкой
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

                        writer.write("");
                        writer.flush();
                        //System.out.println("Содержимое файла успешно очищено.")
                        System.out.println("\n -" + rate + " к балансу!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try (FileWriter fileWriter = new FileWriter(fileNameBal, true)) {
                        fileBalance = Integer.toString(balance);
                        fileWriter.write(fileBalance + "\n");
                    }

                    numLosing+=1;
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileInfoLosing))) {
                        writer.write("");
                        writer.flush();
                        //System.out.println("Содержимое файла успешно очищено.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try (FileWriter fileWriter = new FileWriter(fileNameInfoLosing, true)) {
                        fileWriter.write(numLosing + "\n");
                    }

                    break;
                } else if (score == 21 || croupierScore > 21) {
                    System.out.print("Вы выиграли\n" + "Счет: " + score + ". Счет крупье: " + croupierScore);

                    // Проверка существования файла
                    if (!file.exists()) {
                        System.out.println("Файл не найден или не существует.");
                        return;
                    }
                    // Перезапись файла пустой строкой
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

                        writer.write("");
                        writer.flush();
                        //System.out.println("Содержимое файла успешно очищено.");
                        System.out.println("\n +" + rate + " к балансу");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try (FileWriter fileWriter = new FileWriter(fileNameBal, true)) {
                        fileBalance = Integer.toString(balance);
                        fileWriter.write(fileBalance + "\n");
                    }

                    numWin+=1;
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileInfoWin))) {
                        writer.write("");
                        writer.flush();
                        //System.out.println("Содержимое файла успешно очищено.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try (FileWriter fileWriter = new FileWriter(fileNameInfoWin, true)) {
                        fileWriter.write(numWin + "\n");
                    }

                    break;
                }
                System.out.println("+" + newScore + " к счету! Ваш счет: " + score /*+ " .cr:" + croupierScore*/);

            } else if (choise.equals("2")) {

                if (croupierScore > 21) {

                    System.out.println("Вы выиграли!\n" + "Счет: " + score + ". Счет крупье: " + croupierScore);
                    balance += rate;

                    // Проверка существования файла
                    if (!file.exists()) {
                        System.out.println("Файл не найден или не существует.");
                        return;
                    }
                    // Перезапись файла пустой строкой
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

                        writer.write("");
                        writer.flush();
                        //System.out.println("Содержимое файла успешно очищено.");\
                        System.out.println("\n +" + rate + " к балансу");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try (FileWriter fileWriter = new FileWriter(fileNameBal, true)) {
                        fileBalance = Integer.toString(balance);
                        fileWriter.write(fileBalance + "\n");
                    }

                    numWin+=1;
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileInfoWin))) {
                        writer.write("");
                        writer.flush();
                        //System.out.println("Содержимое файла успешно очищено.")
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try (FileWriter fileWriter = new FileWriter(fileNameInfoWin, true)) {
                        fileWriter.write(numWin + "\n");
                    }

                    break;
                } else if (score > croupierScore && croupierScore < 21) {

                    System.out.println("Вы выиграли!\n" + "Счет: " + score + ". Счет крупье: " + croupierScore);
                    balance += rate;

                    // Проверка существования файла
                    if (!file.exists()) {
                        System.out.println("Файл не найден или не существует.");
                        return;
                    }
                    // Перезапись файла пустой строкой
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

                        writer.write("");
                        writer.flush();
                        //System.out.println("Содержимое файла успешно очищено.");
                        System.out.println("\n +" + rate + " к балансу");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try (FileWriter fileWriter = new FileWriter(fileNameBal, true)) {
                        fileBalance = Integer.toString(balance);
                        fileWriter.write(fileBalance + "\n");
                    }

                    numWin+=1;

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileInfoWin))) {
                        writer.write("");
                        writer.flush();
                        //System.out.println("Содержимое файла успешно очищено.")
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try (FileWriter fileWriter = new FileWriter(fileNameInfoWin, true)) {
                        fileWriter.write(numWin + "\n");
                    }

                    break;
                } else if (score < croupierScore /*&& croupierScore < 21*/) {

                    System.out.println("Вы проиграли!\n" + "Счет: " + score + ". Счет крупье: " + croupierScore);
                    balance -= rate;

                    // Проверка существования файла
                    if (!file.exists()) {
                        System.out.println("Файл не найден или не существует.");
                        return;
                    }
                    // Перезапись файла пустой строкой
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

                        writer.write("");
                        writer.flush();
                        //System.out.println("Содержимое файла успешно очищено.");
                        System.out.println("\n -" + rate + " к балансу");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try (FileWriter fileWriter = new FileWriter(fileNameBal, true)) {
                        fileBalance = Integer.toString(balance);
                        fileWriter.write(fileBalance + "\n");
                    }

                    numLosing+=1;

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileInfoLosing))) {
                        writer.write("");
                        writer.flush();
                        //System.out.println("Содержимое файла успешно очищено.")
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try (FileWriter fileWriter = new FileWriter(fileNameInfoLosing, true)) {
                        fileWriter.write(numLosing + "\n");
                    }

                    break;
                }

            } else {
                System.out.println("Введите еще раз: ");
                continue;
            }
        }

        try (Scanner scanner = new Scanner(new File(fileNameInfoGames))){
            numGames = Integer.parseInt(scanner.nextLine().toString());
        }
        numGames += 1;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileInfoGames))) {
            writer.write("");
            writer.flush();
            //System.out.println("Содержимое файла успешно очищено.")
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter(fileNameInfoGames, true)) {
            fileWriter.write(numGames + "\n");
        }
    }

    public void start() throws IOException {
        while (true) {
            System.out.print("beta!\n1 - Играть\n2 - Выход\n3 - Правила игры\n4 - Статистика\nВвод: ");
            choise2 = sc3.nextLine();
            if (choise2.equals("1")) {
                run();
            } else if (choise2.equals("2")) {
                System.out.println("Завершение работы...");
                break;
            } else if (choise2.equals("3")) {
                try (Scanner scInstilled = new Scanner(new File(fileNameIns))){
                    while (scInstilled.hasNext()) {
                        System.out.println(scInstilled.nextLine());
                    }
                    System.out.println();
                }
                continue;
            } else if (choise2.equals("4")) {
                System.out.println("Статистика:");
                try (Scanner printInfoWin = new Scanner(new File(fileNameInfoWin))){
                        System.out.println("Количество побед: " + printInfoWin.nextLine());
                }
                try (Scanner printInfoLos = new Scanner(new File(fileNameInfoLosing))){
                        System.out.println("Количество проигрышей: " + printInfoLos.nextLine());
                }
                try (Scanner printInfoGam = new Scanner(new File(fileNameInfoGames))){
                        System.out.println("Количество игр: " + printInfoGam.nextLine());
                }
                try (Scanner printInfoBal = new Scanner(new File(fileNameBal))){
                    while (printInfoBal.hasNext())
                        System.out.println("Баланс: " + printInfoBal.nextLine());
                }
                System.out.println();
            } else {
                System.out.println("Еще раз: ");
                 continue;
            }
        }
    }
}
