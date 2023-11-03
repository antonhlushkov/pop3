import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Manager {

    public Semaphore access;  // Семафор для доступу до сховища
    public Semaphore full;    // Семафор, що вказує на повне сховище
    public Semaphore empty;   // Семафор, що вказує на порожнє сховище

    public ArrayList<String> storage = new ArrayList<>();  // Список для зберігання продукції
    public int ic;  // Змінна для зберігання лічильника продукції

    public Manager(int storageSize) {
        access = new Semaphore(1);  // Ініціалізація семафора доступу з однією дозволеною потоку одночасно
        full = new Semaphore(storageSize);  // Ініціалізація семафора "повне сховище" з початковим значенням, рівним максимальній місткості сховища
        empty = new Semaphore(0);  // Ініціалізація семафора "порожнє сховище" з початковим значенням, рівним нулю

        ic = 0;  // Ініціалізація лічильника продукції
    }
}
