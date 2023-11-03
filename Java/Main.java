public class Main {

    public static void main(String[] args) {
        Main main = new Main();  // Створення екземпляру класу Main
        int storageSize = 3;  // Максимальний розмір сховища
        int itemNumbers = 100;  // Загальна кількість продукції
        int num_consumers = 12;  // Кількість споживачів
        int num_producers = 10;  // Кількість виробників
        main.starter(storageSize, itemNumbers, num_consumers, num_producers);  // Виклик методу starter з вказаними параметрами
    }

    private void starter(int storageSize, int itemNumbers, int num_consumers, int num_producers) {
        Manager manager = new Manager(storageSize);  // Створення менеджера з вказаною максимальною місткістю сховища
        for (int i = 0; i < num_consumers; i++) {  // Цикл для створення споживачів
            int itemsPerConsumer = itemNumbers / num_consumers;  // Розрахунок кількості продукції на споживача
            if (i < itemNumbers % num_consumers) {  // Перевірка, чи є залишок продукції для розподілу
                itemsPerConsumer++;  // Якщо так, то додати одну одиницю продукції до поточного споживача
            }
            new Consumer(itemsPerConsumer, manager, "Consumer " + i);  // Створення споживача з обчисленою кількістю продукції
        }
        for (int i = 0; i < num_producers; i++) {  // Цикл для створення виробників
            int itemsPerProducer = itemNumbers / num_producers;  // Розрахунок кількості продукції на виробника
            if (i < itemNumbers % num_producers) {  // Перевірка, чи є залишок продукції для розподілу
                itemsPerProducer++;  // Якщо так, то додати одну одиницю продукції до поточного виробника
            }
            new Producer(itemsPerProducer, manager, "Producer " + i);  // Створення виробника з обчисленою кількістю продукції
        }
    }
}
