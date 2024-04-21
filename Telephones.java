/*
Формат сдачи: ссылка на подписанный git-проект.
***** Задание *****
Реализуйте структуру телефонной книги с помощью HashMap.
Программа также должна учитывать, что в во входной структуре будут повторяющиеся имена с разными телефонами, их необходимо считать, как одного человека с разными телефонами. Вывод должен быть отсортирован по убыванию числа телефонов.
 */

import java.io.*;
import java.util.*;

public class Telephones {
    private static final String FILENAME = "contacts.txt";

    public static void main(String[] args) {
        HashMap<String, List<String>> phoneBook = readFromFile();
        try (Scanner scanner = new Scanner(System.in)) {
            boolean flag = true;
            while (flag) {
                System.out.println(
                        "Укажите действие:\n1 - добавление\n2 - поиск\n3 - удаление\n4 - все контакты\nВыход из программы - любая клавиша.");
                String action = scanner.next();

                if (action.equals("1")) {
                    System.out.println("Укажите имя контакта:");
                    String contName = scanner.next();
                    System.out.println("Укажите номера телефонов через запятую:");
                    String numPhone = scanner.next();
                    String[] phones = numPhone.split(",");

                    if (phoneBook.containsKey(contName)) {
                        List<String> existingPhones = phoneBook.get(contName);
                        for (String phone : phones) {
                            existingPhones.add(phone);
                        }
                    } else {
                        List<String> newPhones = new ArrayList<>();
                        for (String phone : phones) {
                            newPhones.add(phone);
                        }
                        phoneBook.put(contName, newPhones);
                    }

                } else if (action.equals("2")) {
                    System.out.println("Укажите имя контакта:");
                    String searchName = scanner.next();
                    System.out.println(phoneBook.getOrDefault(searchName, new ArrayList<>()).toString());

                } else if (action.equals("3")) {
                    System.out.println("Укажите имя контакта:");
                    String delName = scanner.next();
                    System.out.println("Удалён контакт: " + delName + " тел.: " + phoneBook.remove(delName));

                } else if (action.equals("4")) {
                    if (phoneBook.isEmpty()) {
                        System.out.println("Список контактов пуст");
                    } else {
                        phoneBook.entrySet().stream()
                                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue().size(),
                                        entry1.getValue().size()))
                                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
                    }

                } else {
                    System.out.println("Сохранение контактов в файл и завершение программы...");
                    writeToFile(phoneBook);
                    System.exit(0);
                }
            }
        }
    }

    private static HashMap<String, List<String>> readFromFile() {
        HashMap<String, List<String>> phoneBook = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                String name = parts[0];
                String[] phones = parts[1].split(",");
                if (phoneBook.containsKey(name)) {
                    // Если контакт уже существует, добавляем новые номера телефонов к существующему
                    // списку
                    List<String> existingPhones = phoneBook.get(name);
                    for (String phone : phones) {
                        if (!existingPhones.contains(phone)) {
                            existingPhones.add(phone);
                        }
                    }
                } else {
                    // Если контакта нет, создаем новый список номеров телефонов
                    List<String> phoneList = Arrays.asList(phones);
                    phoneBook.put(name, new ArrayList<>(phoneList));
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        return phoneBook;
    }

    private static void writeToFile(HashMap<String, List<String>> phoneBook) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Map.Entry<String, List<String>> entry : phoneBook.entrySet()) {
                String name = entry.getKey();
                List<String> phones = entry.getValue();
                String phoneString = String.join(",", phones);
                bw.write(name + ":" + phoneString);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}
