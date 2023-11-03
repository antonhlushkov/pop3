public class Consumer implements Runnable {
    private final int itemNumbers;  // Кількість продукції для споживача
    private final Manager manager;  // Менеджер, який управляє сховищем і доступом
    private final String name;  // Назва споживача

    public Consumer(int itemNumbers, Manager manager, String name) {
        this.itemNumbers = itemNumbers;  // Ініціалізація кількості продукції для споживача
        this.manager = manager;  // Ініціалізація менеджера
        this.name = name;  // Ініціалізація назви споживача

        new Thread(this).start();  // Створення нового потоку для споживача та його запуск
    }

    @Override
    public void run() {
        for (int i = 0; i < itemNumbers; i++) {  // Цикл для споживання продукції
            String item;
            try {
                manager.empty.acquire();  // Очікування, доки сховище не стане не порожнім
                Thread.sleep(1000);  // Затримка для симуляції обробки продукції
                manager.access.acquire();  // Очікування доступу до сховища

                item = manager.storage.get(0);  // Отримання продукції зі сховища
                manager.storage.remove(0);  // Видалення продукції зі сховища
                System.out.println(name + " Took " + item);  // Виведення повідомлення про забрану продукцію

                manager.access.release();  // Звільнення доступу до сховища
                manager.full.release();  // Звільнення семафору "повне сховище"

            } catch (InterruptedException e) {
                e.printStackTrace();  // Обробка можливих винятків
            }
        }
    }
}
