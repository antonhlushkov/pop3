public class Producer implements Runnable {
    private final int itemNumbers;  // Кількість продукції, яку виробник повинен виробити
    private final Manager manager;  // Менеджер, який управляє сховищем і доступом
    private final String name;  // Назва виробника

    public Producer(int itemNumbers, Manager manager, String name) {
        this.itemNumbers = itemNumbers;  // Ініціалізація кількості продукції для виробника
        this.manager = manager;  // Ініціалізація менеджера
        this.name = name;  // Ініціалізація назви виробника

        new Thread(this).start();  // Створення нового потоку для виробника та його запуск
    }

    @Override
    public void run() {
        for (int i = 0; i < itemNumbers; i++) {  // Цикл для виробництва продукції
            try {
                manager.full.acquire();  // Очікування, доки сховище не стане не повним
                manager.access.acquire();  // Очікування доступу до сховища

                manager.storage.add("item " + manager.ic);  // Додання нової продукції до сховища з унікальним індексом
                System.out.println(name + " added item " + manager.ic);  // Виведення повідомлення про додану продукцію
                manager.ic++;  // Збільшення індексу продукції

                manager.access.release();  // Звільнення доступу до сховища
                manager.empty.release();  // Звільнення семафору "порожнє сховище"

            } catch (InterruptedException e) {
                e.printStackTrace();  // Обробка можливих винятків
            }
        }
    }
}
