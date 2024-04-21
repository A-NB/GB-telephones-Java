/*
Формат сдачи: ссылка на подписанный git-проект.
***** Задание *****
Реализуйте структуру телефонной книги с помощью HashMap.
Программа также должна учитывать, что в во входной структуре будут повторяющиеся имена с разными телефонами, их необходимо считать, как одного человека с разными телефонами. Вывод должен быть отсортирован по убыванию числа телефонов.
 */

package БезЗаписиВФайл;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Telephones {
    public static void main(String[] args) {
        HashMap<String, List<String>> phoneBook = new HashMap<>();
        try (Scanner scanner = new Scanner(System.in)) {
            boolean flag = true;
            while (flag) {
                System.out.println(
                        "Укажите действие:\n1 - добавление\n2 - поиск\n3 - удаление\n4 - все контакты\nВыход из программы - любая клавиша\n");
                String action = scanner.next();

                if (action.equals("1")) {
                    System.out.println("Укажите имя контакта:");
                    String contName = scanner.next();
                    System.out.println("Укажите номера телефонов через запятую:");
                    String numPhone = scanner.next();
                    String[] phones = numPhone.split(",");

                    if (phoneBook.containsKey(contName)) {
                        // Если контакт уже существует, добавляем новые номера телефонов к существующему
                        // списку
                        List<String> existingPhones = phoneBook.get(contName);
                        for (String phone : phones) {
                            existingPhones.add(phone);
                        }

                    } else {
                        // Если контакта нет, создаём новый список номеров телефонов
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
                    System.out.println("\n");

                } else if (action.equals("3")) {
                    System.out.println("Укажите имя контакта:");
                    String delName = scanner.next();
                    System.out.println("Удалён контакт: " + delName + " тел.: " + phoneBook.remove(delName));
                    System.out.println("\n");

                } else if (action.equals("4")) {

                    if (phoneBook.isEmpty()) {
                        System.out.println("Список контактов пуст");
                        System.out.println("\n");

                    } else {
                        // Выводим контакты, отсортированные по убыванию числа телефонов
                        phoneBook.entrySet().stream()
                                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue().size(),
                                        entry1.getValue().size()))
                                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
                        System.out.println("\n");
                    }

                } else {
                    System.out.println("Программа завершена пользователем...");
                    System.exit(0);
                }
            }
        }
    }
}